package xyz.avalonxr.validation.validator

/**
 * @author Atri
 *
 * A derivative type of [Validator] which validates a list of input values [I] for errors. Any errors that are discovered
 * in the structure of the provided value are returned as instances of result type [R]. Unlike [SingleValidator], this
 * interface is useful for when you need to perform validation within the context of a value in a group.
 *
 * @property I The object type we want to validate.
 * @property R The error type to return for validation errors.
 */
interface MultiValidator<I, R> : Validator<List<I>, R> {

    fun validate(values: List<I>): R?
}