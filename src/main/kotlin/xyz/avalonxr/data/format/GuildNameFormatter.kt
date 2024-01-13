package xyz.avalonxr.data.format

import discord4j.core.`object`.entity.Guild
import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for guild names.
 */
@Component
class GuildNameFormatter : DiscordStringFormatter {

    override fun formatWithContext(context: FormatContext, input: String): String {
        val guild = context.guild
            .map(Guild::getName)
            .block()
            ?: "[Invalid guild]"

        return input
            .replace("\${guild}", guild)
    }
}
