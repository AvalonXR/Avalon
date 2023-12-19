package xyz.avalonxr.enums

import xyz.avalonxr.handler.exit.ApplicationExitHandler
import xyz.avalonxr.handler.exit.ExceptionOnError
import xyz.avalonxr.handler.exit.LogOnExit
import xyz.avalonxr.handler.exit.DebugOnExit

/**
 * @author Atri
 *
 * A limited scope property that is mapped by configuration classes. This provides a collection of valid property states
 * that a user may provide, and the corresponding [ApplicationExitHandler] mapping. The value provided by the user will
 * determine how the application handles shutdown requests.
 *
 * @see LogOnExit
 * @see ExceptionOnError
 * @see DebugOnExit
 */
@Suppress("unused")
enum class ExitHandlerStrategy(
    val handler: ApplicationExitHandler
) {

    /**
     * Log exit as INFO or ERROR for each shutdown signal corresponding to if the exit code maps to an error.
     */
    LOG(LogOnExit),

    /**
     * Throw an exception whenever a shutdown signal mapping to an error code is received.
     */
    EXCEPTION(ExceptionOnError),

    /**
     * Silently log all shutdown signals at DEBUG level.
     */
    DEBUG(DebugOnExit),
}