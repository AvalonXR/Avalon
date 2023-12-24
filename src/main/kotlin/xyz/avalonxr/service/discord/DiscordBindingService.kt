package xyz.avalonxr.service.discord

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import xyz.avalonxr.binding.discord.command.GlobalCommandBinder
import xyz.avalonxr.binding.discord.command.GuildCommandBinder
import xyz.avalonxr.provider.DiscordClientProvider
import xyz.avalonxr.service.command.CommandService

/**
 * @author Atri
 *
 * This service provides functionality necessary for binding all commands in our application to their appropriate
 * descriptors on Discord's side. The setup for this service falls into three phases, stale deletion, create/update
 * binding definitions, and command binding.
 *
 * - Old, unused binding definitions are first removed from Discord to provide us with more room to work with.
 * - Existing commands have their definitions created on Discord's side, or updated if already present.
 * - All commands in the app are matched to their corresponding descriptors, and be automatically executed when called.
 */
@Service
class DiscordBindingService @Autowired constructor(
    private val globalCommandBinder: GlobalCommandBinder,
    private val guildCommandBinder: GuildCommandBinder,
    private val discordClientProvider: DiscordClientProvider,
    private val commandService: CommandService,
) {

    /**
     * Initializes the service following the 3-phase lifecycle described above.
     */
    fun initialize() {
        // Delete unused command definitions
        deleteStaleBindings()
        // Create new or update existing command definitions
        createOrUpdateBindingDefinitions()
        // Bind all commands to the appropriate descriptor
        bindCommands()
        // Ready message
        val commands = commandService
            .getAllCommands()
            .map { it.getBinding().request.name() }
        logger.info("Binding service ready! Commands bound: $commands")
    }

    private fun deleteStaleBindings() {
        logger.info("Deleting stale bindings")
        globalCommandBinder.deleteStaleCommands()
        guildCommandBinder.deleteStaleCommands()
    }

    private fun createOrUpdateBindingDefinitions() {
        logger.info("Creating or updating command binding definitions")
        // Split commands by scope
        val (globalCommands, guildCommands) = commandService
            .getAllCommands()
            .partition { it.getBinding().scope.isGlobal() }
        // Bind each command list to their respective scopes
        globalCommandBinder.bind(globalCommands)
        guildCommandBinder.bind(guildCommands)
    }

    private fun bindCommands() {
        logger.info("Binding commands to definitions...")
        // Bind command definitions to executors
        discordClientProvider
            .provide()
            .on(ChatInputInteractionEvent::class.java) {
                Mono.just(commandService.processCommand(it))
            }
            .subscribe()
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(DiscordBindingService::class.java)
    }
}
