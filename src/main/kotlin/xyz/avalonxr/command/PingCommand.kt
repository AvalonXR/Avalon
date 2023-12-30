package xyz.avalonxr.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import xyz.avalonxr.annotations.AvalonCommand
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.command.CommandBinding
import xyz.avalonxr.dsl.discord.OptionType
import xyz.avalonxr.dsl.discord.command
import xyz.avalonxr.dsl.discord.option
import xyz.avalonxr.dsl.discord.optional
import xyz.avalonxr.enums.CommandScope
import xyz.avalonxr.models.discord.Command

@AvalonCommand
class PingCommand : Command {

    override fun getBinding(): CommandBinding = CommandBinding(
        command("ping") {
            description("Pong!")

            option(OptionType.STRING) {
                name("text")
                description("Response Text")
                optional()
            }
        },
        CommandScope.GUILD
    )

    override fun processCommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult {
        val text = options
            .getOrDefault<String>("text", "Pong!")

        return CommandResult.success(text)
    }
}
