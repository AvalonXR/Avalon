package xyz.avalonxr.service.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.enums.ExitCode
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.service.LifecycleService

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
        // Collect command context data, TODO we may want to change this to encapsulate things in a data class
        val user = event.interaction.user
        val guildId = event.interaction.guildId.orElse(null)
        val options = event.options.toOptionStore()
        // Run command logic with context
        logger.info("Command '$commandName' executed by ${user.username}")
        return command.processCommand(event, user, guildId, options)
    }

    fun findCommandByName(name: String): Command? = commandCache[name]

    fun findCommandsByNames(names: Collection<String>): List<Command> = commandCache
        .filter { (name) -> name in names }
        .values
        .toList()

    @EventListener(ApplicationStartedEvent::class)
    fun getAllCommands(): List<Command> = commandCache.values.toList()

    // While this likely isn't an immediate issue, I don't think this currently
    // handles subcommands or groups correctly. We should evaluate this later on.
    private fun List<ApplicationCommandInteractionOption>.toOptionStore(): OptionStore = this
        .map { it.name to it.value }
        .toTypedArray()
        .let { OptionStore(*it) }

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
            .associateBy { it.getCommandDescriptor().name() }
            .also { logger.info("Command service ready! Registered commands: ${it.keys}") }
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(CommandService::class.java)
    }
}
