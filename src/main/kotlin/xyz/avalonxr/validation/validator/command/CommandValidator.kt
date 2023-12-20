package xyz.avalonxr.validation.validator.command

import xyz.avalonxr.data.error.ValidationError
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.validation.validator.MultiValidator

/**
 * @author Atri
 *
 * A derivative of [MultiValidator] that acts as a collection type for command validators. These are used in determining
 * whether the list of commands present in the application are uniform, such that we can quickly determine which command
 * is meant to be called.
 *
 * @see CheckForDuplicates
 */
interface CommandValidator : MultiValidator<Command, ValidationError>
