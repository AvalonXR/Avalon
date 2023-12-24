package xyz.avalonxr.binding.discord.command

import xyz.avalonxr.models.discord.Command

/**
 * @author Atri
 *
 * A contract describing the required functionality for command binders. This is limited-scope and only defined
 * internally, currently derived by [GlobalCommandBinder] and [GuildCommandBinder].
 */
sealed interface CommandBinder {

    /**
     * Binds the provided command list to Discord's command binding definitions. This must be specified as either
     * GLOBAL or GUILD. Commands will then be partitioned and provided to the correct binder based on this value.
     *
     * @param commands The list of commands to process in this binder context.
     */
    fun bind(commands: List<Command>)

    /**
     * Processes the existing list of commands for Discord's binding definitions to remove any stale (no longer in use)
     * definitions. This is done as a way to ensure that old commands are no longer kept around after being removed or
     * disabled, should this ever occur. This function should be called before [bind] as to ensure the bot does not
     * accidentally overload the allowed command count which Discord allows for either context.
     */
    fun deleteStaleCommands()
}
