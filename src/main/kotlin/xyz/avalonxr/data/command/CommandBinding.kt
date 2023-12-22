package xyz.avalonxr.data.command

import discord4j.discordjson.json.ApplicationCommandRequest
import xyz.avalonxr.models.discord.Command

/**
 * @author Atri
 *
 * A container object which holds all data that is associated with an instance of [Command].
 *
 * @property request The command request which is sent over to Discord during the binding process.
 */
data class CommandBinding(
    val request: ApplicationCommandRequest
)
