package xyz.avalonxr.dsl.discord

import discord4j.core.spec.EmbedCreateFields
import discord4j.core.spec.EmbedCreateSpec

@DslMarker
annotation class EmbedDsl

@EmbedDsl
fun embed(builder: EmbedCreateSpec.Builder.() -> Unit): EmbedCreateSpec = EmbedCreateSpec
    .builder()
    .also(builder)
    .build()

@EmbedDsl
data class FieldGroupContext(
    internal val fields: MutableList<EmbedCreateFields.Field> = mutableListOf()
) {

    @EmbedDsl
    fun field(name: String, value: String) = EmbedCreateFields.Field
        .of(name, value, true)
        .let(fields::add)
}

@EmbedDsl
fun EmbedCreateSpec.Builder.field(
    name: String,
    value: String
): EmbedCreateSpec.Builder =
    addField(name, value, false)

@EmbedDsl
fun EmbedCreateSpec.Builder.fieldGroup(
    context: FieldGroupContext.() -> Unit
) = FieldGroupContext()
    .also(context)
    .fields
    .map { addField(it.name(), it.value(), true) }
    .also { addField(" ", " ", false) }
