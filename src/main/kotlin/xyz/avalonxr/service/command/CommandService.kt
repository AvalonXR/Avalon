package xyz.avalonxr.service.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec
import discord4j.rest.util.Color
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.OptionValue
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.data.error.CommonError
import xyz.avalonxr.dsl.discord.embed
import xyz.avalonxr.enums.CommandScope
import xyz.avalonxr.enums.ExitCode
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.service.LifecycleService
import kotlin.jvm.optionals.getOrNull

@Service
class CommandService @Autowired constructor(
    private val commands: List<Command>,
    private val commandValidatorService: CommandValidationService,
    private val lifecycleService: LifecycleService,
) {
    private val commandCache: Map<String, Command> by lazy { initialize() }

    fun processCommand(event: ChatInputInteractionEvent): CommandResult {
        // Check that the cache has been initialized before we continue
        val commandName = event.commandName
        // Make sure the user sends us a valid command
        // Todo: We likely should have this divert to a non-command default handler
        val command = commandCache[commandName]
            ?: return CommandResult.failure(CommandError.CommandNotFound(commandName))
        // Collect command option data
        val options = event.options.toOptionStore()
        // Run command logic with context
        logger.info("Command '$commandName' executed by ${event.interaction.user.username}")
        return runCatching { command.processCommand(event, options) }
            .onFailure { defaultHandler(event, it) }
            .getOrDefault(CommandResult.failure(CommonError.GeneralError))
    }

    fun findCommandByName(name: String): Command? = commandCache[name]

    fun findCommandsByNames(names: Collection<String>): List<Command> = commandCache
        .filter { (name) -> name in names }
        .values
        .toList()

    fun getAllCommands(): List<Command> = commandCache
        .values
        .toList()

    fun hasGlobalCommand(name: String): Boolean = commandCache[name]
        ?.takeIf { it.getBinding().scope == CommandScope.GLOBAL } != null

    fun hasGuildCommand(name: String): Boolean = commandCache[name]
        ?.takeIf { it.getBinding().scope == CommandScope.GUILD } != null

    // While this likely isn't an immediate issue, I don't think this currently
    // handles subcommands or groups correctly. We should evaluate this later on.
    private fun List<ApplicationCommandInteractionOption>.toOptionStore(): OptionStore = this
        .mapNotNull(::processToPair)
        .toTypedArray()
        .let { OptionStore(*it) }

    private fun processToPair(value: ApplicationCommandInteractionOption): Pair<String, OptionValue>? = value
        .value
        .getOrNull()
        ?.let { value.name to OptionValue(it, value.type) }

    private fun defaultHandler(
        event: ChatInputInteractionEvent,
        exception: Throwable?
    ): CommandResult {
        val embed = embed {
            color(Color.RED)
            title("⚠️ Error 500")
            description("Whoops! Looks like an error occurred running that command. Try again later!")
        }
        exception?.printStackTrace()
        event
            .reply(
                InteractionApplicationCommandCallbackSpec.create()
                    .withEmbeds(embed)
                    .withEphemeral(true)
            )
            .subscribe()
        return CommandResult.failure(CommonError.GeneralError)
    }

    private fun initialize(): Map<String, Command> {
        logger.info("Starting command service...")
        // Short-circuit if no commands are present
        if (commands.isEmpty()) {
            logger.info("No commands in registry! Command service ready!")
            return emptyMap()
        }
        // Check for validation errors, shut down with error log if any exist
        val errors = commandValidatorService.validate(commands)
        if (errors.isNotEmpty()) {
            lifecycleService.shutdown(ExitCode.INITIALIZATION_FAILURE, this::class.java, errors)
        }
        // Cache validated commands into list
        return commands
            .associateBy { it.getBinding().request.name() }
            .also { logger.info("Command service ready! Registered commands: ${it.keys}") }
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(CommandService::class.java)
    }
}
