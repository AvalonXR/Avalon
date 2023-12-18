package xyz.avalonxr.validation.service

import xyz.avalonxr.validation.validator.Validator

/**
 * @author Atri
 *
 * Describes a service dedicated to validating a given input against a set of validators. This class should not be
 * referenced directly. Rather, any validation logic should instead derive from either [SingleValidationService] or
 * [MultiValidationService].
 *
 * @property T The type of validator to collect.
 * @property validators The list of collected validators.
 */
interface ValidationService<T : Validator<*, *>> {

    /**
     * A collected list of validators matching type [T].
     */
    val validators: List<T>
}
