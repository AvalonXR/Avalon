package xyz.avalonxr.handler.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import org.springframework.stereotype.Component
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.dsl.discord.embed
import xyz.avalonxr.utils.replyWithEmbed

@Component
class AvalonDefaultCommandHandler : DefaultCommandHandler {

    override fun commandNotFound(
        event: ChatInputInteractionEvent,
        command: String
    ): CommandResult = CommandError
        .CommandNotFound(command)
        .let(CommandResult::failure)

    override fun commandFailed(
        event: ChatInputInteractionEvent,
        command: String,
        exception: Throwable?
    ): CommandResult {
        exception?.printStackTrace()
        getFailureEmbed(command)
            .let(event::replyWithEmbed)
            .subscribe()
        return CommandError
            .CommandFailed(command)
            .let(CommandResult::failure)
    }

    private fun getFailureEmbed(command: String): EmbedCreateSpec = embed {
        color(Color.RED)
        title("⚠️ Error 500")
        description("Looks like an error occurred when executing the command $command. Try again later!")
    }
}
