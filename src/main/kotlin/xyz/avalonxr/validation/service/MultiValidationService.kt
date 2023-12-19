package xyz.avalonxr.validation.service

import xyz.avalonxr.validation.validator.MultiValidator

/**
 * @author Atri
 *
 * A derivative type of [ValidationService] which validates a list of input values [I] against a collection of
 * validators conforming to the provided type [T]. Any errors that are discovered in the structure of the provided value
 * are returned as instances of result type [R]. Unlike [SingleValidationService], this interface is useful for when you
 * need to perform validation within the context of a value in a group.
 *
 * @property I The object type we want to validate.
 * @property R The error type to return for validation errors.
 * @property T The classifier which is used to determine which validators are collected.
 */
interface MultiValidationService<I, R, T : MultiValidator<I, R>> : ValidationService<T> {

    /**
     * Validates the provided [values] list against the collected [validators] list. Any errors derived from this
     * process should be collected as instances of error type [R].
     *
     * @param values The input value list to validate.
     *
     * @return A collection of validation errors.
     */
    fun validate(values: List<I>): List<R> = validators
        .mapNotNull { it.validate(values) }
}
