package xyz.avalonxr.provider

/**
 * @author Atri
 *
 * A stereotype which describes a class which is meant to provide a value dependent on some form of external value. This
 * can be used to map enum values used in configuration files to corresponding concrete logic classes. An example of
 * this in action is seen in [ExitStrategyHandleProvider].
 *
 * @property T The concrete logic type we should expect the implementation to provide.
 */
interface Provider<T> {

    /**
     * Provides a value corresponding to the given provider type [T].
     *
     * @return An object matching the given provider type.
     */
    fun provide(): T
}
