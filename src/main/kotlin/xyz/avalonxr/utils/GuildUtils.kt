package xyz.avalonxr.utils

import discord4j.common.util.Snowflake
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import kotlin.jvm.optionals.getOrNull

/**
 * @author Atri
 *
 * A utility function for retrieving the guild ID of a given event. This function should not be called outside of guild
 * scopes where a guild ID is present. If this is called from such a scope, it will always return null.
 *
 * @return The guild ID corresponding to the command event, or null if the event originates from outside a guild.
 */
fun ChatInputInteractionEvent.guildIdAsLong(): Long? = interaction
    .guildId
    .map(Snowflake::asLong)
    .getOrNull()
