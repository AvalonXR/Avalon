package xyz.avalonxr.exception

import xyz.avalonxr.data.OptionStore
import kotlin.reflect.KType

/**
 * @author Atri
 *
 * A derivative of [AvalonException] which can occur in [OptionStore] when the requested type is not supported.
 *
 * @param type The requested type which is not supported.
 */
class UnsupportedOptionTypeException(type: KType) :
    AvalonException("Unsupported option type '$type' was requested!")
