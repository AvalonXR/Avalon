package xyz.avalonxr.data.error

sealed class CommandError(message: String) : AvalonError(message) {

    class CommandNotFound(commandName: String) : CommandError("Could not find command matching name: $commandName")

    class CommandFailed(commandName: String) : CommandError("Command $commandName failed to execute!")

    data object CommandNotFromGuild : CommandError("Command must be sent from a guild!")

    data object IncorrectChannelType : CommandError("Incorrect channel type!")

    class InvalidSubcommand(vararg subcommands: String) :
        CommandError("Subcommand not valid! Please specify command from list: $subcommands")
}
