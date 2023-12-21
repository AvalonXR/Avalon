package xyz.avalonxr.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import xyz.avalonxr.enums.ExitStrategy
import xyz.avalonxr.handler.exit.LogOnExit

/**
 * @author Atri
 *
 * A configuration class which provides the list of base settings that this app requires to function. These are
 * currently specified in the application's `application.yml` file.
 */
@Configuration
data class AppSettings(

    /**
     * The exit strategy to use if an exception has occurred within the application's runtime. This defaults to
     * [LogOnExit] should the config value not be provided, or considered invalid.
     */
    @Value("\${avalon.exit.strategy:LOG}")
    val exitStrategy: ExitStrategy,

    /**
     * The token associated with the bot account we want our application to log into. This token being present is
     * necessary for the application to function.
     */
    @Value("\${discord.token:}")
    val discordClientToken: String,
)
