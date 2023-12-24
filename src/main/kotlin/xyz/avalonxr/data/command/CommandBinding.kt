package xyz.avalonxr.data.command

import discord4j.discordjson.json.ApplicationCommandRequest
import xyz.avalonxr.enums.CommandScope
import xyz.avalonxr.models.discord.Command

/**
 * @author Atri
 *
 * A container object which holds all data that is associated with an instance of [Command].
 *
 * @property request The command request which is sent over to Discord during the binding process.
 * @property scope The location which this command is mapped to. This can currently be either GLOBAL or GUILD.
 */
data class CommandBinding(
    val request: ApplicationCommandRequest,
    val scope: CommandScope = CommandScope.GLOBAL,
)
