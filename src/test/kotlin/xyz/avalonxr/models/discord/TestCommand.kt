package xyz.avalonxr.models.discord

import discord4j.common.util.Snowflake
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.entity.User
import discord4j.discordjson.json.ApplicationCommandRequest
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.OptionStore
import xyz.avalonxr.dsl.discord.command

object TestCommand : Command {

    override fun getCommandDescriptor(): ApplicationCommandRequest = command("foo")

    override fun processCommand(
        source: ChatInputInteractionEvent,
        user: User,
        guildId: Snowflake?,
        options: OptionStore
    ): CommandResult = CommandResult.success()
}
