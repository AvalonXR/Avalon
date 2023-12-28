package xyz.avalonxr.handler.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.handler.Handler

/**
 * @author Atri
 *
 * A contract for describing handlers which are primarily meant to be used for providing default logic in the event that
 * an executed command fails, either when executing an invalid or outdated command, or when a command throws an error.
 */
interface DefaultCommandHandler : Handler {

    /**
     * Called after a command is executed, successfully or unsuccessfully, as a way of handling commands with no
     * provided Discord-facing response. This can be useful in making sure user's actually see an error or success
     * message when the command does not explicitly send one on its own.
     *
     * @param event The corresponding event for which the command is connected to.
     * @param result The result of the command's execution, whether successful or unsuccessful.
     */
    fun afterExecution(event: ChatInputInteractionEvent, result: CommandResult)

    /**
     * Called when an executed command does not exist in the application. This can occur if the command system provides
     * a descriptor for a command which no longer has any implementation.
     *
     * @param event The corresponding event for which the command is connected to.
     * @param command The name of the command which was called.
     *
     * @return A command result corresponding to when a command is not found.
     */
    fun commandNotFound(event: ChatInputInteractionEvent, command: String): CommandResult

    /**
     * Called when an executed command throws an unexpected exception. This functions as a default response to sandbox
     * command runtime so that we may use exceptions when necessary without crashing the application.
     *
     * @param event The corresponding event for which the command is connected to.
     * @param command The name of the command for which an exception was thrown.
     * @param exception The thrown exception resulting from our command.
     *
     * @return A command result corresponding to when an exception is thrown.
     */
    fun commandFailed(event: ChatInputInteractionEvent, command: String, exception: Throwable?): CommandResult
}
