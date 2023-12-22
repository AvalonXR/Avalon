package xyz.avalonxr.exception

/**
 * A global [RuntimeException] type which functions as a grouping class for all exceptions associated with the
 * application. While using exceptions in most cases should be avoided, there remains a few areas where this approach
 * is far more desirable than using result type returns.
 *
 * We should ideally only use these to help indicate where very specific code errors may occur, where validation as a
 * solution is not sufficiently viable.
 *
 * @param message The message associated with the exception.
 */
sealed class AvalonException(message: String) : RuntimeException(message)
