package xyz.avalonxr.data.format

import discord4j.core.`object`.entity.channel.Channel
import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for channel mentions.
 */
@Component
class ChannelMentionFormatter : DiscordStringFormatter {

    override fun formatWithContext(context: FormatContext, input: String): String {
        val channel = context
            .channel
            .map(Channel::getMention)
            .block()
            ?: "[Invalid channel]"

        return input
            .replace("\${channel}", channel)
    }
}
