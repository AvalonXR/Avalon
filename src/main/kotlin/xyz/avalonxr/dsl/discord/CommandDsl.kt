package xyz.avalonxr.dsl.discord

import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandOptionData
import discord4j.discordjson.json.ImmutableApplicationCommandRequest
import xyz.avalonxr.annotations.dsl.CommandDsl

typealias CommandRequestBuilder =
    ImmutableApplicationCommandRequest.Builder

typealias OptionDataBuilder =
    ImmutableApplicationCommandOptionData.Builder

@CommandDsl
fun command(
    name: String,
    builder: CommandRequestBuilder.() -> Unit = {}
): ApplicationCommandRequest = ApplicationCommandRequest
    .builder()
    .name(name)
    .also(builder)
    .build()

@CommandDsl
fun CommandRequestBuilder.option(
    type: OptionType,
    builder: OptionDataBuilder.() -> Unit
): CommandRequestBuilder = ApplicationCommandOptionData
    .builder()
    .type(type.value)
    .required(true)
    .also(builder)
    .build()
    .let(::addOption)

@CommandDsl
fun OptionDataBuilder.option(
    type: OptionType,
    builder: OptionDataBuilder.() -> Unit
): OptionDataBuilder = ApplicationCommandOptionData
    .builder()
    .type(type.value)
    .required(true)
    .also(builder)
    .build()
    .let(::addOption)

@CommandDsl
fun OptionDataBuilder.optional(): OptionDataBuilder =
    required(false)

fun String.formatCapitalCase(): String = "[ _]"
    .toRegex()
    .let(::split)
    .joinToString(" ") {
        it.lowercase()
            .replaceFirstChar(Char::uppercase)
    }

@CommandDsl
inline fun <reified T : Enum<T>> OptionDataBuilder.choices(): OptionDataBuilder =
    enumValues<T>()
        .map {
            ApplicationCommandOptionChoiceData
                .builder()
                .name(it.name.formatCapitalCase())
                .value(it.name)
                .build()
        }
        .let { addAllChoices(it) }

@CommandDsl
@Suppress("unused")
enum class OptionType(val value: Int) {

    UNKNOWN(-1),

    SUB_COMMAND(1),

    SUB_COMMAND_GROUP(2),

    STRING(3),

    INTEGER(4),

    BOOLEAN(5),

    USER(6),

    CHANNEL(7),

    ROLE(8),

    MENTIONABLE(9),

    NUMBER(10),

    ATTACHMENT(11)
}
