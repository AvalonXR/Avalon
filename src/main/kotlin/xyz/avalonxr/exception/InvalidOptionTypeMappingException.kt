package xyz.avalonxr.exception

import discord4j.core.`object`.command.ApplicationCommandOption.Type
import xyz.avalonxr.data.OptionStore
import kotlin.reflect.KType

/**
 * @author Atri
 *
 * A derivative of [AvalonException] which occurs in [OptionStore]. This exception can occur when a request is made for
 * a value in an option store which is requested as a type differing from the one described in the command definition.
 *
 * @param field The field name of the option which was marked invalid.
 * @param provided The type discord provides for our field, as mapped by the corresponding command binding.
 * @param expected The type requested by the receiver. We expect this to be different from the provided type.
 */
class InvalidOptionTypeMappingException(field: String, provided: Type, expected: KType) :
    AvalonException("Could not convert '$field' with type $provided to $expected")
