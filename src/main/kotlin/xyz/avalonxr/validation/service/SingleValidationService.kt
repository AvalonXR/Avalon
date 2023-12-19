package xyz.avalonxr.validation.service

import xyz.avalonxr.validation.validator.SingleValidator

/**
 * @author Atri
 *
 * A derivative type of [ValidationService] which validates a singleton input value [I] against a collection of
 * validators conforming to the provided type [T]. Any errors that are discovered in the structure of the provided value
 * are returned as instances of result type [R].
 *
 * @property I The object type we want to validate.
 * @property R The error type to return for validation errors.
 * @property T The classifier which is used to determine which validators are collected.
 */
interface SingleValidationService<I, R, T : SingleValidator<I, R>> : ValidationService<T> {

    /**
     * Validates the provided [value] against the collected [validators] list. Any errors derived from this process
     * should be collected as instances of error type [R].
     *
     * @param value The input value to validate.
     *
     * @return A collection of validation errors.
     */
    fun validate(value: I): List<R> = validators
        .mapNotNull { it.validate(value) }
}
