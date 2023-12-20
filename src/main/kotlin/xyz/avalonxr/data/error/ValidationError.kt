package xyz.avalonxr.data.error

/**
 * @author Atri
 *
 *  A grouping type derivative of [AvalonError] meant to contain error or success messages associated with validation.
 */
sealed class ValidationError(message: String) : AvalonError(message) {

    class DuplicateCommandNames(names: Collection<String>) :
        ValidationError("Command list contains duplicated names: $names")
}
