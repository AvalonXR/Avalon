package xyz.avalonxr.handler.exit

import xyz.avalonxr.enums.ExitCode
import kotlin.jvm.Throws

/**
 * @author Atri
 *
 * A derivative of [ApplicationExitHandler] which throws an exception containing the specified error message whenever an
 * error exit code is encountered. This can be helpful for debugging if the application appears to randomly crash with no
 * explanation.
 */
data object ExceptionOnError : ApplicationExitHandler {

    @Throws(IllegalStateException::class)
    override fun handleExit(code: ExitCode, vararg extras: Any?): Int {
        val message = code.message.format(*extras)

        if (code.isError) {
            throw IllegalStateException(message)
        }

        return code.code
    }
}