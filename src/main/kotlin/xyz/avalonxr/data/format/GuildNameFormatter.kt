package xyz.avalonxr.data.format

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for guild names.
 */
@Component
class GuildNameFormatter : DiscordStringFormatter {

    override fun formatWithContext(event: ChatInputInteractionEvent, input: String): String {
        val guild = event.interaction.guild
            .block()
            ?.name
            ?: "Unknown"

        return input.replace("\${guild}", guild)
    }
}
