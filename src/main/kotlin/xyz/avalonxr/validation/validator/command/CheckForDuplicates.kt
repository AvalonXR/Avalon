package xyz.avalonxr.validation.validator.command

import org.springframework.stereotype.Component
import xyz.avalonxr.data.error.ValidationError
import xyz.avalonxr.models.discord.Command

/**
 * @author Atri
 *
 * A derivative of [CommandValidator] which checks if all commands in the provided command list have a unique command
 * name. If the provided list does not conform to the constraints provided by this validator, a validation error for
 * [ValidationError.DuplicateCommandNames] is returned. If no error is detected, then null is returned instead.
 */
@Component
class CheckForDuplicates : CommandValidator {

    override fun validate(values: List<Command>): ValidationError? {
        val visited = mutableSetOf<String>()
        val duplicates = mutableSetOf<String>()

        for (value in values) {
            val name = value.getBinding().request.name()

            if (name in visited) {
                duplicates.add(name)
            }

            visited.add(name)
        }

        return ValidationError
            .DuplicateCommandNames(duplicates)
            .takeIf { duplicates.isNotEmpty() }
    }
}
