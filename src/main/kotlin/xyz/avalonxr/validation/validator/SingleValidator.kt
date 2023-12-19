package xyz.avalonxr.validation.validator

/**
 * @author Atri
 *
 * A derivative type of [Validator] which evaluates a singleton input value [I] for errors. Any errors that are
 * discovered in the structure of the provided value are returned as instances of result type [R].
 *
 * @property I The object type we want to validate.
 * @property R The error type to return for validation errors, or null if no error is discovered.
 */
interface SingleValidator<I, R> : Validator<I, R> {

    fun validate(value: I): R?
}
