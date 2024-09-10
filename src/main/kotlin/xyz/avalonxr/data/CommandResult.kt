package xyz.avalonxr.data

import xyz.avalonxr.data.CommandResult.Failure
import xyz.avalonxr.data.CommandResult.Info
import xyz.avalonxr.data.CommandResult.Success
import xyz.avalonxr.data.error.AvalonError

/**
 * @author Atri
 *
 * A class structure used specifically in determining command execution status. This comes in several varying states,
 * including [Success], [Failure], and [Info]. Each state represents and is useful for portraying to the application the
 * resulting execution state of a given command. This integrates with our default command handler to display status via
 * embed sidebar color.
 */
sealed class CommandResult {

    /**
     * Represents a successful command execution. Useful for representing to the application that a command returned a
     * successful result.
     *
     * @property payload The associated payload.
     */
    data class Success(val payload: Any?) : CommandResult()

    /**
     * Represents a failed command execution. Useful for representing to the application that a command returned a
     * bad result.
     *
     * @property error The associated error object.
     */
    data class Failure(val error: AvalonError) : CommandResult()

    /**
     * Represents a command result which does not change regardless of execution. Useful for commands or subcommands
     * that simply return a message.
     *
     * @property message The associated info message.
     */
    data class Info(val message: String) : CommandResult()

    /**
     * Whether the object in question is a failure state.
     *
     * @return True if the result is a [Failure] state.
     */
    fun isError(): Boolean = this is Failure

    companion object {

        /**
         * Creates a [Success] instance.
         *
         * @param value The value associated with this result.
         *
         * @return A [Success] object containing the corresponding [value].
         */
        fun success(value: Any? = null): CommandResult = Success(value)

        /**
         * Creates a [Failure] instance.
         *
         * @param error The error associated with this result.
         *
         * @return A [Failure] object containing the corresponding [error].
         */
        fun failure(error: AvalonError): CommandResult = Failure(error)

        /**
         * Creates an [Info] instance.
         *
         * @param info The message associated with this result.
         *
         * @return An [Info] object containing the corresponding [info] message.
         */
        fun info(info: String): CommandResult = Info(info)
    }
}

/**
 * Creates a [Success] instance.
 *
 * @param value The value associated with this result.
 *
 * @return A [Success] object containing the corresponding [value].
 */
fun Any?.success(): CommandResult = CommandResult.success(this)

/**
 * Creates a [Failure] instance.
 *
 * @param error The error associated with this result.
 *
 * @return A [Failure] object containing the corresponding [error].
 */
fun AvalonError.failure(): CommandResult = CommandResult.failure(this)

/**
 * Creates an [Info] instance.
 *
 * @param info The message associated with this result.
 *
 * @return An [Info] object containing the corresponding [info] message.
 */
fun String.info(): CommandResult = CommandResult.info(this)


