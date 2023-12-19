package xyz.avalonxr.handler.exit

import org.slf4j.LoggerFactory
import xyz.avalonxr.enums.ExitCode

/**
 * @author Atri
 *
 * A derivative of [ApplicationExitHandler] which will do nothing except log the exit message at debug level. This limits
 * log output in all exit cases, however messages can still be seen when log level is set to DEBUG.
 */
data object DebugOnExit : ApplicationExitHandler {

    override fun handleExit(
        code: ExitCode,
        vararg extras: Any?
    ): Int = logger
        .debug(code.message.format(*extras))
        .let { code.code }

    private val logger = LoggerFactory
        .getLogger(DebugOnExit::class.java)
}