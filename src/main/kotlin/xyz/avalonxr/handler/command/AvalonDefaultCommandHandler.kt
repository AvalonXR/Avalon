package xyz.avalonxr.handler.command

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import org.springframework.stereotype.Component
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.dsl.discord.embed
import xyz.avalonxr.utils.replyWithEmbed
import xyz.avalonxr.utils.sendMessage

@Component
class AvalonDefaultCommandHandler : DefaultCommandHandler {

    override fun afterExecution(event: ChatInputInteractionEvent, result: CommandResult) {
        // Do not respond again if the command has already provided a reply
        if (event.reply.block() == null) {
            return
        }

        val message = result.data
            ?.message
            ?: "Command executed successfully!"

        event.sendMessage(message)
    }

    override fun commandNotFound(
        event: ChatInputInteractionEvent,
        command: String
    ): CommandResult {
        val message = CommandError
            .CommandNotFound(command)

        event.sendMessage(message.message)

        return message
            .let(CommandResult::failure)
    }

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
