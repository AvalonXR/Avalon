package xyz.avalonxr.binding.discord.command

import discord4j.core.GatewayDiscordClient
import discord4j.rest.service.ApplicationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.Disposable
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.provider.DiscordClientProvider
import xyz.avalonxr.service.command.CommandService

/**
 * @author Atri
 *
 * Manages command bindings for the GLOBAL command scope. This will work with our binding service to tear down and
 * update our command definitions as needed.
 */
@Component
class GlobalCommandBinder @Autowired constructor(
    private val discordClientProvider: DiscordClientProvider,
    private val commandService: CommandService
) : CommandBinder {

    private val client: GatewayDiscordClient by lazy {
        discordClientProvider.provide()
    }

    private val appService: ApplicationService by lazy {
        client.restClient.applicationService
    }

    override fun bind(commands: List<Command>) {
        if (commands.isEmpty()) {
            logger.info("No commands to bind in global command scope")
            return
        }

        logger.info("Overwriting bindings for provided commands in global command scope")
        commands
            .map { it.getBinding().request }
            .let { appService.bulkOverwriteGlobalApplicationCommand(discordClientProvider.appId, it) }
            .subscribe()
    }

    override fun deleteStaleCommands() {
        val appId = discordClientProvider.appId
        appService
            .getGlobalApplicationCommands(appId)
            .filter { !commandService.hasGlobalCommand(it.name()) }
            .map {
                val commandId = it.id().asLong()
                logger.info("Deleting stale global command definition: '${it.name()}'")
                deleteCommand(appId, commandId)
            }
            .subscribe()
    }

    private fun deleteCommand(appId: Long, commandId: Long): Disposable = appService
        .deleteGlobalApplicationCommand(appId, commandId)
        .subscribe()

    companion object {

        private val logger = LoggerFactory
            .getLogger(GlobalCommandBinder::class.java)
    }
}
