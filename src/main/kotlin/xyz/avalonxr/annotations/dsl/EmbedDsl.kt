package xyz.avalonxr.annotations.dsl

import discord4j.core.spec.EmbedCreateSpec

/**
 * @author Atri
 *
 * This marker annotation exists primarily to notate classes and functions which are associated with our internal
 * embed builder DSL. We use this as a way to help describe the structure of [EmbedCreateSpec] objects to our app.
 */
@DslMarker
annotation class EmbedDsl
