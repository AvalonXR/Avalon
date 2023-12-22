package xyz.avalonxr.data

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Attachment
import discord4j.core.`object`.entity.Role
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.Channel
import xyz.avalonxr.exception.AvalonException
import xyz.avalonxr.exception.InvalidOptionTypeMappingException
import xyz.avalonxr.exception.UnsupportedOptionTypeException
import kotlin.jvm.Throws
import kotlin.reflect.typeOf

/**
 * @author Atri
 *
 * A container class which exists to help with processing options stored in command events. This primarily provides
 * quality-of-life functionality which will make it easier to read back fields from the event during command execution.
 *
 * @param options A collection of all fields associated with a command's execution.
 */
class OptionStore(
    vararg options: Pair<String, OptionValue>
) : Map<String, OptionValue> by mapOf(*options) {

    /**
     * Finds a field present within the given [OptionStore] instance, mapping it to the requested type [T] if present.
     * This function may in some circumstances throw an exception deriving from [AvalonException] depending on if the
     * requested type is not supported or incorrectly mapped.
     *
     * @param name The name of the option field to retrieve.
     *
     * @throws UnsupportedOptionTypeException When the requested type does not conform to any supported return types.
     * @throws InvalidOptionTypeMappingException When the field type differs from the type requested.
     *
     * @return The corresponding field value mapped to type [T], or null if no value is present.
     */
    @Throws(UnsupportedOptionTypeException::class, InvalidOptionTypeMappingException::class)
    inline fun <reified T : Any> findByName(name: String): T? {
        // Retrieve field from option store
        val (option, type) = get(name)
            ?: return null
        // Process type mappings
        return runCatching {
            when (typeOf<T>()) {
                typeOf<Any>() -> option.raw as? T
                typeOf<String>() -> option.asString() as? T
                typeOf<Long>() -> option.asLong() as? T
                typeOf<Snowflake>() -> option.asSnowflake() as? T
                typeOf<Int>() -> option.asLong().toInt() as? T
                typeOf<Double>() -> option.asDouble() as? T
                typeOf<Boolean>() -> option.asBoolean() as? T
                typeOf<User>() -> option.asUser().block() as? T
                typeOf<Channel>() -> option.asChannel().block() as? T
                typeOf<Role>() -> option.asRole().block() as? T
                typeOf<Attachment>() -> option.asAttachment() as? T
                else -> throw UnsupportedOptionTypeException(typeOf<T>())
            }
        }
            .onFailure { throw InvalidOptionTypeMappingException(name, type, typeOf<T>()) }
            .getOrNull()
    }
}
