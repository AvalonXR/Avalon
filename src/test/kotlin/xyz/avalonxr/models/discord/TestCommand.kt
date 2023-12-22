package xyz.avalonxr.models.discord

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.data.command.CommandBinding
import xyz.avalonxr.dsl.discord.command

object TestCommand : Command {

    override fun getBinding(): CommandBinding = CommandBinding(
        command("foo")
    )

    override fun processCommand(
        source: ChatInputInteractionEvent,
        options: OptionStore
    ): CommandResult = CommandResult.success()
}
