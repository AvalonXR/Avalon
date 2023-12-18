package xyz.avalonxr.validation.validator

/**
 * @author Atri
 *
 * Describes an object dedicated to validating a given input for errors. This class should not be referenced directly.
 * Rather, any validation logic should instead derive from either [SingleValidator] or [MultiValidator].
 *
 * @property I The input type to validate.
 * @property R The resulting error type to return, should validation errors be discovered.
 */
interface Validator<I, R>
