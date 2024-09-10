package xyz.avalonxr.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.entity.channel.TextChannel
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import xyz.avalonxr.annotations.AvalonCommand
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.command.CommandBinding
import xyz.avalonxr.data.entity.WelcomeMessage
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.data.failure
import xyz.avalonxr.data.info
import xyz.avalonxr.data.success
import xyz.avalonxr.dsl.discord.OptionType
import xyz.avalonxr.dsl.discord.command
import xyz.avalonxr.dsl.discord.option
import xyz.avalonxr.dsl.discord.optional
import xyz.avalonxr.dsl.discord.subCommand
import xyz.avalonxr.enums.CommandScope
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.repository.WelcomeMessageRepository
import xyz.avalonxr.service.FormatService
import xyz.avalonxr.utils.getGuildChannelById
import xyz.avalonxr.utils.guildIdAsLong
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull

@AvalonCommand
@Transactional
class WelcomeCommand @Autowired constructor(
    private val messageRepository: WelcomeMessageRepository,
    private val formatService: FormatService,
) : Command {

    override fun getBinding(): CommandBinding = CommandBinding(
        command("welcome") {
            description("Welcome command")

            subCommand {
                name("set")
                description("Configure welcome message")

                option(OptionType.STRING) {
                    name("message")
                    description("Welcome Message")
                }

                option(OptionType.CHANNEL) {
                    name("channel")
                    description("Welcome Channel")
                    optional()
                }
            }

            subCommand {
                name("preview")
                description("Preview welcome message")

                option(OptionType.BOOLEAN) {
                    name("formatted")
                    description("Preview formatted or un-formatted message")
                    optional()
                }
            }

            subCommand {
                name("clear")
                description("Clear welcome message")
            }
        },
        CommandScope.GUILD
    )

    override fun processCommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult = when (options.subCommand) {
        "set" -> setSubcommand(source, options)
        "preview" -> previewSubcommand(source, options)
        "clear" -> clearSubcommand(source)
        else -> CommandError
            .InvalidSubcommand("set", "preview", "clear")
            .failure()
    }

    private fun setSubcommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult {
        val guildId = source.guildIdAsLong()
            ?: return CommandError.CommandNotFromGuild.failure()
        val defaultChannel = source.interaction.channel
            .block()
            ?: return CommandError.IncorrectChannelType.failure()
        val channel = options
            .findChannel<TextChannel>("channel")
            .onInvalid { return CommandResult.failure(CommandError.IncorrectChannelType) }
            .getOrDefault(defaultChannel)
        val channelName = channel.mention
        val message = options
            .getOrDefault<String>("message", "Welcome!")
        val welcomeMessage = messageRepository
            .findByGuildId(guildId)
            .getOrDefault(WelcomeMessage(guildId, channel.id.asLong(), message))

        welcomeMessage.message = message
        welcomeMessage.channelId = channel.id.asLong()
        messageRepository.save(welcomeMessage)

        return "Configured welcome message in $channelName"
            .success()
    }

    private fun previewSubcommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult {
        val formatted = options
            .getOrDefault("formatted", false)
        val guildId = source.guildIdAsLong()
            ?: return CommandError.CommandNotFromGuild.failure()
        val welcomeMessage = messageRepository
            .findByGuildId(guildId)
            .getOrNull()
        val content = welcomeMessage?.message
            ?: return "No welcome message configured.".info()
        val channelName = source
            .getGuildChannelById(welcomeMessage.channelId)
            ?.mention
            ?: "None Provided"
        val message = when (formatted) {
            true -> formatService.formatForCommand(source, content)
            else -> content
        }

        return "\"$message\" (in channel: $channelName)".success()
    }

    private fun clearSubcommand(source: ChatInputInteractionEvent): CommandResult {
        val guildId = source.guildIdAsLong()
            ?: return CommandError.CommandNotFromGuild.failure()

        messageRepository.deleteByGuildId(guildId)

        return "Cleared welcome message.".info()
    }
}
