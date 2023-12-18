package xyz.avalonxr.dsl.discord

import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.discordjson.json.ImmutableApplicationCommandOptionData
import discord4j.discordjson.json.ImmutableApplicationCommandRequest

@DslMarker
annotation class CommandDsl

@CommandDsl
fun command(
    name: String,
    builder: ImmutableApplicationCommandRequest.Builder.() -> Unit = {}
): ApplicationCommandRequest = ApplicationCommandRequest
    .builder()
    .name(name)
    .also(builder)
    .build()

@CommandDsl
fun ImmutableApplicationCommandRequest.Builder.option(
    type: OptionType,
    builder: ImmutableApplicationCommandOptionData.Builder.() -> Unit
): ImmutableApplicationCommandRequest.Builder = ApplicationCommandOptionData
    .builder()
    .type(type.value)
    .required(true)
    .also(builder)
    .build()
    .let(::addOption)

@CommandDsl
fun ImmutableApplicationCommandOptionData.Builder.optional(): ImmutableApplicationCommandOptionData.Builder =
    required(false)

fun String.formatCapitalCase(): String = "[ _]"
    .toRegex()
    .let(::split)
    .joinToString(" ") {
        it.lowercase()
            .replaceFirstChar(Char::uppercase)
    }

@CommandDsl
inline fun <reified T : Enum<T>> ImmutableApplicationCommandOptionData.Builder.choices(): ImmutableApplicationCommandOptionData.Builder =
    enumValues<T>()
        .map {
            ApplicationCommandOptionChoiceData
                .builder()
                .name(it.name.formatCapitalCase())
                .value(it.name)
                .build()
        }
        .let { addAllChoices(it) }

@EmbedDsl
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