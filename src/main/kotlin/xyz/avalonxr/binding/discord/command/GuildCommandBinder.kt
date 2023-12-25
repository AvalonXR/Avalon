package xyz.avalonxr.binding.discord.command

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.guild.GuildCreateEvent
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.service.ApplicationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.provider.DiscordClientProvider
import xyz.avalonxr.service.command.CommandService
import xyz.avalonxr.utils.blockList

/**
 * @author Atri
 *
 * Manages command bindings for the GUILD command scope. This will work with our binding service to tear down and update
 * our command definitions as needed.
 */
@Component
class GuildCommandBinder @Autowired constructor(
    private val discordClientProvider: DiscordClientProvider,
    private val commandService: CommandService,
) : CommandBinder {

    private val client: GatewayDiscordClient by lazy {
        discordClientProvider.provide()
    }

    private val appService: ApplicationService by lazy {
        client.restClient.applicationService
    }

    override fun bind(commands: List<Command>) {
        if (commands.isEmpty()) {
            logger.info("No commands to bind in guild command scope")
            return
        }
        // Register guild commands
        val appId = discordClientProvider.appId
        val requests = commands
            .map { it.getBinding().request }
        val guilds = client
            .guilds
            .map { it.id.asLong() }
        // Generate command bindings for guilds on load or join
        logger.info("Overwriting bindings for provided commands in guild command scope")
        setupExistingGuilds(appId, guilds, requests)
        setupGuildListener(appId, requests, guilds)
    }

    override fun deleteStaleCommands() {
        val appId = discordClientProvider.appId
        // Process each guild's command list as it is initialized in code
        client
            .on(GuildCreateEvent::class.java) { event ->
                val guildId = event.guild.id.asLong()
                deleteStaleCommandsForGuild(appId, guildId)
            }
            .subscribe()
    }

    private fun setupExistingGuilds(appId: Long, guildIds: Flux<Long>, commands: List<ApplicationCommandRequest>) {
        guildIds
            .map { id ->
                logger.info("Overwriting bindings for provided commands in guild ID '$id' command scope")
                appService
                    .bulkOverwriteGuildApplicationCommand(appId, id, commands)
                    .subscribe()
            }
            .subscribe()
    }

    private fun setupGuildListener(appId: Long, requests: List<ApplicationCommandRequest>, ignoreGuildIds: Flux<Long>) {
        val guilds = ignoreGuildIds
            .blockList()
        // Setup registration listener
        client
            .on(GuildCreateEvent::class.java) {
                val id = it.guild.id.asLong()
                when (id in guilds) {
                    // Skip previously registered guilds
                    true -> {
                        logger.info("Guild $id previously registered, skipping...")
                        Mono.empty()
                    }
                    // Register newly joined guild
                    else -> {
                        logger.info("Generating bindings for provided commands in guild ID '$id' command scope")
                        Mono.just(appService.bulkOverwriteGuildApplicationCommand(appId, id, requests))
                    }
                }
            }
            .subscribe()
    }

    private fun deleteStaleCommandsForGuild(appId: Long, guildId: Long): Flux<Disposable> = appService
        .getGuildApplicationCommands(appId, guildId)
        .filter { !commandService.hasGuildCommand(it.name()) }
        .map {
            val commandId = it.id().asLong()
            logger.info("Deleting stale guild command definition '${it.name()}' by ID '$commandId'")
            deleteCommand(appId, guildId, commandId)
        }

    private fun deleteCommand(appId: Long, guildId: Long, commandId: Long): Disposable = appService
        .deleteGuildApplicationCommand(appId, guildId, commandId)
        .subscribe()

    companion object {

        private val logger = LoggerFactory
            .getLogger(GlobalCommandBinder::class.java)
    }
}
