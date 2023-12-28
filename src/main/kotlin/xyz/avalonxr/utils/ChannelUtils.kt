package xyz.avalonxr.utils

import discord4j.common.util.Snowflake
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.entity.channel.Channel

/**
 * @author Atri
 *
 * Retrieves a channel from a guild via its channel ID. This will return a value only if executed on an event which
 * originates within a guild. If this is executed on an event outside of this scope, it will return null.
 *
 * @param channelId The ID of the channel which we want to retrieve.
 *
 * @return The channel that was found, if present, or null if the channel doesn't exist within the context of the event.
 */
fun ChatInputInteractionEvent.getGuildChannelById(channelId: Long?): Channel? = channelId
    ?.let(Snowflake::of)
    ?.let(::getGuildChannelById)

/**
 * @author Atri
 *
 * Retrieves a channel from a guild via its channel ID. This will return a value only if executed on an event which
 * originates within a guild. If this is executed on an event outside of this scope, it will return null.
 *
 * @param channelId The ID of the channel which we want to retrieve.
 *
 * @return The channel that was found, if present, or null if the channel doesn't exist within the context of the event.
 */
fun ChatInputInteractionEvent.getGuildChannelById(
    channelId: Snowflake?,
): Channel? = when (channelId) {
    null -> null
    else ->
        interaction
            .guild
            .flatMap { it?.getChannelById(channelId) }
            .block()
}
