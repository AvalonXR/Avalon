package xyz.avalonxr.message

/**
 * @author Atri
 *
 * A collection object for all error messages associated with application shutdown, either expected or unexpected.
 */
sealed class ExitMessage(message: String) : Message(message) {

    data object ExitOk : ExitMessage("Termination signal received, exit OK")

    data object InitFailed : ExitMessage("Failed to initialize application at {0} phase: {1}")
}