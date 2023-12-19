package xyz.avalonxr.models

import discord4j.core.event.domain.Event
import kotlin.reflect.KClass

typealias EventClass =
    KClass<out Event>

typealias ListenerFunction =
    (Event) -> Unit
