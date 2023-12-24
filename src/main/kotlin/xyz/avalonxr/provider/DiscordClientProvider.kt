package xyz.avalonxr.provider

import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.avalonxr.config.AppSettings
import xyz.avalonxr.enums.ExitCode
import xyz.avalonxr.service.LifecycleService

/**
 * @author Atri
 *
 * A derivative of [Provider] which handles generating and distributing of [GatewayDiscordClient] instances. This
 * functions effectively as an entrypoint to the Discord API.
 *
 * @property appSettings The application's configuration file, this contains the client's API token.
 * @property lifecycleService The application's lifecycle management component. Used for shutting down the app
 * gracefully, should the client fail to generate for any reason.k
 */
@Component
class DiscordClientProvider @Autowired constructor(
    private val appSettings: AppSettings,
    private val lifecycleService: LifecycleService,
) : Provider<GatewayDiscordClient> {
    private var discordClient: GatewayDiscordClient? = null

    /**
     * Provides the app's ID. We do not expect this to change once this is generated.
     */
    val appId: Long by lazy {
        provide().restClient.applicationId.block() ?: -1
    }

    override fun provide(): GatewayDiscordClient {
        // Generate the discord client when requested, if one does not already exist
        if (discordClient == null) {
            logger.info("No client available! Attempting to log into Discord...")
            discordClient = generateClient()
                ?.also { logger.info("Client logged into ${it.self.block()?.username}!") }
        }
        // Return the client or force a shutdown if a client fails to be generated
        return discordClient
            ?: fatal("Failed to create discord client!")
    }

    // Generate a simple client using the provided API token
    private fun generateClient(): GatewayDiscordClient? = appSettings
        .discordClientToken
        .runCatching(DiscordClient::create)
        .getOrElse { fatal("API Token not provided!") }
        .login()
        .onErrorMap { fatal("API Token was not valid!") }
        .block()

    // Shut down the application with a given message
    private fun fatal(message: String): Nothing = lifecycleService
        .shutdown(ExitCode.INITIALIZATION_FAILURE, this::class.simpleName, message)

    companion object {

        private val logger = LoggerFactory
            .getLogger(DiscordClientProvider::class.java)
    }
}
