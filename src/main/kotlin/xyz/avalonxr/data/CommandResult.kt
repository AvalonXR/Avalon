package xyz.avalonxr.data

import xyz.avalonxr.data.error.AvalonError

class CommandResult private constructor(
    val error: AvalonError? = null
) {
    fun isError(): Boolean =
        error != null

    companion object {

        fun ofSuccess(): CommandResult =
            CommandResult()

        fun ofFailure(
            error: AvalonError
        ) = CommandResult(error)
    }
}
