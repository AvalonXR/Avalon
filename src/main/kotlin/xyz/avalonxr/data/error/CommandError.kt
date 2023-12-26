package xyz.avalonxr.data.error

sealed class CommandError(message: String) : AvalonError(message) {

    class CommandNotFound(commandName: String) : CommandError("Could not find command matching name: $commandName")

    class CommandFailed(commandName: String) : CommandError("Command $commandName failed to execute!")
}
