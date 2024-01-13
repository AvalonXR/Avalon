package xyz.avalonxr.models.discord

import xyz.avalonxr.data.format.FormatContext

/**
 * @author Atri
 *
 * A definition for components which specialize specifically in string formatting. Derivatives of this will replace an
 * input template string with another specified value. This allows for messages to provide dynamic responses to actions
 * occurring in various different contexts.
 */
interface DiscordStringFormatter {

    /**
     * Formats a given [input] using context provided via an event.
     *
     * @param context The event which contains the relevant context for formatting with.
     * @param input The input string to format.
     */
    fun formatWithContext(context: FormatContext, input: String): String
}
