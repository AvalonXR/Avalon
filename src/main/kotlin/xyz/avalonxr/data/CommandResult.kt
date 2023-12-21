package xyz.avalonxr.data

import xyz.avalonxr.data.error.AvalonError

class CommandResult private constructor(
    error: AvalonError? = null,
) : Payload<AvalonError>(error) {

    fun isError(): Boolean = data != null

    companion object {

        fun success(): CommandResult =
            CommandResult()

        fun failure(
            error: AvalonError
        ) = CommandResult(error)
    }
}
