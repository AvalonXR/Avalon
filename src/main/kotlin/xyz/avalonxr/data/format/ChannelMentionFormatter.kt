package xyz.avalonxr.data.format

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for channel mentions.
 */
@Component
class ChannelMentionFormatter : DiscordStringFormatter {

    override fun formatWithContext(event: ChatInputInteractionEvent, input: String): String {
        val channel = event.interaction.channel
            .block()
            ?.mention
            ?: "Unknown"

        return input
            .replace("\${channel}", channel)
    }
}
