package xyz.avalonxr.data

import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.`object`.command.ApplicationCommandOption

/**
 * @author Atri
 *
 * A container class which holds fields associated primarily with an option field. This is used primarily in the
 * implementation of [OptionStore] to provide added context which we otherwise wouldn't have.
 *
 * @property option The command option value container associated with our field.
 * @property type The expected type of the associated field.
 */
data class OptionValue(
    val option: ApplicationCommandInteractionOptionValue,
    val type: ApplicationCommandOption.Type,
)
