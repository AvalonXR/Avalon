package xyz.avalonxr.data

/**
 * @author Atri
 *
 * A three-way boxed class which is helpful for encapsulating values which may be missing or invalid.
 *
 * @property T The type of the optional we want to return as the inner data type.
 */
sealed class ValidatedOptional<T> {

    /**
     * A state class of [ValidatedOptional] which describes a valid state for the boxed element. We can assume that
     * [data] is always provided in this instance.
     *
     * @param T The underlying type of the value we want to retrieve.
     * @param data The underlying value which was validated.
     */
    data class Valid<T>(val data: T) : ValidatedOptional<T>()

    /**
     * A state class of [ValidatedOptional] which describes an invalid state corresponding to type [T]. This will return
     * or contain no data, and is primarily useful for providing branching explicit logic support.
     *
     * @param T The underlying type of the value we want to retrieve. This will not be used in this object.
     */
    class Invalid<T> : ValidatedOptional<T>()

    /**
     * A state class of [ValidatedOptional] which describes an empty state corresponding to type [T]. This will return
     * or contain no data, and is primarily useful for providing branching explicit logic support.
     *
     * @param T The underlying type of the value we want to retrieve. This will not be used in this object.
     */
    class Empty<T> : ValidatedOptional<T>()
}
