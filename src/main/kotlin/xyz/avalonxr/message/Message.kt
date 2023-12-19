package xyz.avalonxr.message

import java.text.MessageFormat

/**
 * @author Atri
 *
 * A permissive, but internally restricted class used for constructing messages that can be passed around the application
 * freely. Message instances must derive their state from an internal [template] string, which can only be retrieved via
 * the provided [format] function.
 *
 * @param template The string template which will be formatted according to our inputs.
 */
sealed class Message(
    private val template: String
) {

    /**
     * Constructs a proper message string from our included [template]. This can vary depending on which input values are
     * provided to the function.
     *
     * @param values The values we want to display within the message.
     *
     * @return The formatted message.
     */
    fun format(vararg values: Any?): String = MessageFormat
        .format(template, *values)
}