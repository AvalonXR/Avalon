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

    /**
     * Executes a lambda function if the current state of the object is [Invalid]. This will not modify the underlying
     * object state and only should be used for executing code that does not try to mutate the object's state.
     *
     * @param block The function to execute when the object is [Invalid].
     *
     * @return The original object.
     */
    inline fun onInvalid(block: () -> Unit): ValidatedOptional<T> {
        if (this is Invalid) {
            block()
        }

        return this
    }

    /**
     * Processes a lambda function if the object state is [Invalid]. The result of this lambda, whether null or present
     * will be mapped to [Empty] or [Valid] for each respective value. This can be used for coalescence should the state
     * of this object be undesired.
     *
     * @param block The function to execute when the object is [Invalid].
     *
     * @return The updated object, mapping to [Empty] or [Valid].
     */
    inline fun mapInvalid(block: () -> T?): ValidatedOptional<T> {
        if (this !is Invalid) {
            return this
        }

        return when (val result = block()) {
            null -> Empty()
            else -> Valid(result)
        }
    }

    /**
     * Executes a lambda function if the current state of the object is [Empty]. This will not modify the underlying
     * object state and only should be used for executing code that does not try to mutate the object's state.
     *
     * @param block The function to execute when the object is [Empty].
     *
     * @return The original object.
     */
    inline fun onEmpty(block: () -> Unit): ValidatedOptional<T> {
        if (this is Empty) {
            block()
        }

        return this
    }

    /**
     * Processes a lambda function if the object state is [Empty]. The result of this lambda, whether null or present
     * will be mapped to [Empty] or [Valid] for each respective value. This can be used for coalescence should the state
     * of this object be undesired.
     *
     * @param block The function to execute when the object is [Empty].
     *
     * @return The updated object, mapping to [Empty] or [Valid].
     */
    inline fun mapEmpty(block: () -> T?): ValidatedOptional<T> {
        if (this !is Empty) {
            return this
        }

        return when (val result = block()) {
            null -> Empty()
            else -> Valid(result)
        }
    }

    /**
     * Retrieves the underlying boxed value mapped to type [D], or as a default value should the object state be either
     * [Invalid] or [Empty].
     *
     * @param default The default value to retrieve if the object state is not [Valid].
     *
     * @return The underlying value, or [default] if the object state is invalid or empty.
     */
    @Suppress("UNCHECKED_CAST")
    fun <D> getOrDefault(default: D): D = when (this) {
        is Invalid,
        is Empty -> default
        is Valid -> data as D
    }
}
