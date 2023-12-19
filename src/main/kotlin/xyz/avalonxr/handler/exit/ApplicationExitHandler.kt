package xyz.avalonxr.handler.exit

import xyz.avalonxr.enums.ExitCode
import xyz.avalonxr.handler.Handler
import xyz.avalonxr.service.LifecycleService

/**
 * @author Atri
 *
 * A derivative of [Handler] which specifically focuses on what actions to perform when a shutdown signal is received
 * by the [LifecycleService]. This interface may not be inherited externally, and has only 3 direct descendants.
 *
 * @see DebugOnExit
 * @see ExceptionOnError
 * @see LogOnExit
 */
sealed interface ApplicationExitHandler : Handler {

    /**
     * Handles an exit signal provided by [LifecycleService]. This function should perform any pre-process final
     * operations before returning the exit code to spring. It should be expected that the application will be
     * terminated shortly after this function is executed, if called in the context of [LifecycleService].
     *
     * @param code The [ExitCode] signal to process.
     * @param extras Any extra values that may be useful to provide (typically for logging).
     */
    fun handleExit(code: ExitCode, vararg extras: Any?): Int
}
