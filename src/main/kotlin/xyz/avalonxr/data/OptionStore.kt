package xyz.avalonxr.data

import discord4j.common.util.Snowflake
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.`object`.entity.Attachment
import discord4j.core.`object`.entity.Role
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.Category
import discord4j.core.`object`.entity.channel.Channel
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.entity.channel.NewsChannel
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.`object`.entity.channel.StoreChannel
import discord4j.core.`object`.entity.channel.TextChannel
import discord4j.core.`object`.entity.channel.VoiceChannel
import xyz.avalonxr.exception.AvalonException
import xyz.avalonxr.exception.InvalidOptionTypeMappingException
import xyz.avalonxr.exception.UnsupportedOptionTypeException
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull
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
    vararg options: Pair<String, OptionValue>,
    val subCommand: String? = null,
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

    /**
     * Finds a field present within the given [OptionStore] instance, mapping it to the requested type [T] if present.
     * This function may in some circumstances throw an exception deriving from [AvalonException] depending on if the
     * requested type is not supported or incorrectly mapped.
     *
     * @throws UnsupportedOptionTypeException When the requested type does not conform to any supported return types.
     * @throws InvalidOptionTypeMappingException When the field type differs from the type requested.
     *
     * @return The corresponding field value mapped to type [T], or [default] if no value is present.
     */
    @Throws(UnsupportedOptionTypeException::class, InvalidOptionTypeMappingException::class)
    inline fun <reified T : Any> getOrDefault(
        name: String,
        default: T
    ): T = findByName<T>(name)
        ?: default

    /**
     * Finds a channel by a given subtype of [Channel]. This is useful in the instance that we need a specific type of
     * channel for part of our application's processes. We recommend using this for the following types: [Category],
     * [VoiceChannel], [StoreChannel], [TextChannel], [NewsChannel], or [PrivateChannel]. [MessageChannel] may be used
     * if you want to accept any text channel.
     *
     * @param name The name of the property to retrieve.
     *
     * @return The channel corresponding to the name of the option, or null if the channel isn't present or unable to
     * be cast to the provided type.
     */
    inline fun <reified T : Channel> findChannel(name: String): T? = findByName<Channel>(name) as? T

    companion object {

        /**
         * Converts an [ApplicationCommandInteractionOption] list to an instance of [OptionStore]. This will map command
         * options to a flat storage map, regardless of if it's a subcommand or root command store. This will simplify
         * access to values passed into the command, making it easier to write command logic without relying on
         * significant amounts of boilerplate code. The corresponding subcommand will be provided via the [subCommand]
         * field, if present, to help with command routing.
         *
         * @param options The command option list that we want to process.
         *
         * @return An option store containing values for the most relevant command or subcommand.
         */
        fun toOptionStore(options: List<ApplicationCommandInteractionOption>): OptionStore {
            val sub = options
                .firstOrNull { it.type == ApplicationCommandOption.Type.SUB_COMMAND }

            if (sub != null) {
                return toOptionStoreWithSubCommand(sub.options, sub.name)
            }

            return options
                .mapNotNull(::processToPair)
                .toTypedArray()
                .let { OptionStore(*it) }
        }

        private fun toOptionStoreWithSubCommand(
            subOptions: List<ApplicationCommandInteractionOption>,
            subCommand: String? = null,
        ): OptionStore = subOptions
            .mapNotNull(::processToPair)
            .toTypedArray()
            .let { OptionStore(*it, subCommand = subCommand) }

        private fun processToPair(value: ApplicationCommandInteractionOption): Pair<String, OptionValue>? = value
            .value
            .getOrNull()
            ?.let { value.name to OptionValue(it, value.type) }
    }
}
