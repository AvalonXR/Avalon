package xyz.avalonxr.events

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import xyz.avalonxr.service.discord.DiscordBindingService

/**
 * @author Atri
 *
 * An event listener which manages the application's startup procedures. In this case, this is primarily initializing
 * the linkage of our command engine to discord. Once this is enabled, the application will be able to receive events
 * related to command interactions, and be able to process them into meaningful information.
 */
@Component
@Profile("prod")
class SpringApplicationStartedListener(
    private val discordBindingService: DiscordBindingService
) : ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        // Initialize the binder service
        discordBindingService.initialize()
    }
}
