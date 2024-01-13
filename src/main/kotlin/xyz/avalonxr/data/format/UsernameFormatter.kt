package xyz.avalonxr.data.format

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
        context: FormatContext,
        input: String
    ): String {
        val user = context.user.block()
        val name = user?.username ?: "[Invalid username]"
        val mention = user?.mention ?: "[Invalid mention]"

        return input
            .replace("\${username.username}", name)
            .replace("\${username.mention}", mention)
    }
}
