package xyz.avalonxr.enums

import xyz.avalonxr.handler.exit.ApplicationExitHandler
import xyz.avalonxr.message.ExitMessage
import xyz.avalonxr.message.Message

/**
 * @author Atri
 *
 * Exit code message mapping class which can be used to determine what the cause of the application's shutdown was. This
 * is used primarily in conjunction with derivatives of [ApplicationExitHandler] to determine what should be done
 * depending on the [code] provided by an instance of this enum.
 *
 * @property code The exit code to associate with this signal.
 * @property message The cause or log message to send in relation to this exit code.
 */
enum class ExitCode(
    val message: Message,
    val code: Int = 0,
) {

    OK(ExitMessage.ExitOk),
    INITIALIZATION_FAILURE(ExitMessage.InitFailed, 1),
    ;

    /**
     * Determines if the signal is an error code or not. An exit signal should only be considered successful if the
     * value of the exit code is 0. In all other cases, assume the error code means something else.
     */
    val isError: Boolean = code != 0
}
