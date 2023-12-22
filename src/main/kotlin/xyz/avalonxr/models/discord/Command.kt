package xyz.avalonxr.models.discord

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.command.CommandBinding

/**
 * @author Atri
 *
 * A contract which is provided to allow spring to register and retrieve all existing commands and their corresponding
 * command bindings. This enables us to keep data together with the corresponding logic mapping.
 */
interface Command {

    /**
     * Retrieves the binding data associated with our command. This data is used primarily for providing structural data
     * to Discord which will allow us to enforce a contract between our app and the service.
     *
     * @return The binding associated with our command.
     */
    fun getBinding(): CommandBinding

    /**
     * This provides the command logic we want to associate with our [CommandBinding] data. Using the data present in
     * the binding function, we can easily pull back data from the command as it's passed in for processing.
     *
     * @param source The originating [ChatInputInteractionEvent] associated with the execution of this command.
     * @param options The generated [OptionStore] which contains all relevant options derived from our [source] event.
     *
     * @return The execution result of our command, specifying either pass or fail.
     */
    fun processCommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult
}
