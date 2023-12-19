package xyz.avalonxr.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import xyz.avalonxr.enums.ExitHandlerStrategy
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
    val exitStrategy: ExitHandlerStrategy
)
