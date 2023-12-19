package xyz.avalonxr.handler.exit

import org.slf4j.LoggerFactory
import xyz.avalonxr.enums.ExitCode

/**
 * @author Atri
 *
 * A derivative of [ApplicationExitHandler] which will log the exit message as INFO or ERROR depending on the exit code
 * type. This performs no other actions prior to exiting the application.
 */
data object LogOnExit : ApplicationExitHandler {

    override fun handleExit(code: ExitCode, vararg extras: Any?): Int {
        val message = code.message.format(*extras)

        when (code.isError) {
            true -> logger.error(message)
            else -> logger.info(message)
        }

        return code.code
    }

    private val logger = LoggerFactory
        .getLogger(LogOnExit::class.java)
}
