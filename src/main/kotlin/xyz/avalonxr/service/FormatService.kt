package xyz.avalonxr.service

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import xyz.avalonxr.data.format.FormatContext
import xyz.avalonxr.models.discord.DiscordStringFormatter

/**
 * @author Atri
 *
 * A service dedicated to formatting strings based on instances of [DiscordStringFormatter] provided by the application.
 * This is useful for providing an easy way to format strings according to a standard template set, specifically for
 * rich-text feature support in Discord.
 *
 * @param formatters The formatters provided by the application which will process any inputs passed to it.
 */
@Service
class FormatService @Autowired constructor(
    private val formatters: List<DiscordStringFormatter>
) {

    /**
     * Formats a given [input] string using the internal formatters provided by the application.
     *
     * @param event The event which contains the relevant context for formatting with.
     * @param input The input string to format.
     */
    fun formatForCommand(event: ChatInputInteractionEvent, input: String): String {
        val context = FormatContext(
            guild = event.interaction.guild,
            channel = event.interaction.channel,
            member = Mono.justOrEmpty(event.interaction.member),
            user = Mono.just(event.interaction.user),
        )
        return process(context, input)
    }

    fun formatWithContext(context: FormatContext, input: String): String = process(context, input)

    private fun process(context: FormatContext, input: String): String {
        logger.debug("Running formatter over input string: $input")
        // Feed input through each formatter and return
        var current = input
        formatters
            .forEach { current = it.formatWithContext(context, current) }

        return current
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(FormatService::class.java)
    }
}
