package xyz.avalonxr.service.command.events

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import xyz.avalonxr.events.handler.DiscordEventHandler
import xyz.avalonxr.provider.DiscordClientProvider

/**
 * @author Atri
 */
@Service
class DiscordEventService
@Autowired @Lazy
constructor(
    private val eventHandlers: List<DiscordEventHandler>,
    private val discordClientProvider: DiscordClientProvider,
) {

    fun initialize() {
        logger.info("Initializing Discord event bus...")
        val client = discordClientProvider.provide()
        eventHandlers.forEach {
            logger.info("Initializing event class ${it::class.simpleName}...")
            it.setup(client)
        }
        logger.info("Event bus initialized!")
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(DiscordEventService::class.java)
    }
}
