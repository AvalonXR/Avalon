package xyz.avalonxr.annotations.dsl

import discord4j.discordjson.json.ApplicationCommandRequest

/**
 * @author Atri
 *
 * This marker annotation exists primarily to notate classes and functions which are associated with our internal
 * command builder DSL. We use this as a way to help describe the structure of [ApplicationCommandRequest] objects
 * to our app.
 */
@DslMarker
annotation class CommandDsl
