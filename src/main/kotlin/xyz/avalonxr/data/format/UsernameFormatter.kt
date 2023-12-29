package xyz.avalonxr.data.format

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for usernames and user mentions.
 */
@Component
class UsernameFormatter : DiscordStringFormatter {

    override fun formatWithContext(
        event: ChatInputInteractionEvent,
        input: String
    ): String {
        val user = event.interaction.user

        return input
            .replace("\${username.username}", user.username)
            .replace("\${username.mention}", user.mention)
    }
}
