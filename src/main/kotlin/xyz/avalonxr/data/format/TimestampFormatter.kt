package xyz.avalonxr.data.format

import org.springframework.stereotype.Component
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A derivative of [DiscordStringFormatter] which provides formatting support for rich timestamps.
 */
@Component
class TimestampFormatter : DiscordStringFormatter {

    private val now: Long
        get() = System.currentTimeMillis() / 1000

    override fun formatWithContext(context: FormatContext, input: String): String = input
        .replace("\${now.date}", "<t:$now:d>")
        .replace("\${now.dateText}", "<t:$now:D>")
        .replace("\${now.time}", "<t:$now:t>")
        .replace("\${now.full}", "<t:$now:F>")
        .replace("\${now.relative}", "<t:$now:R>")
}
