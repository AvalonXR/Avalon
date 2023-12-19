package xyz.avalonxr.models.discord

import discord4j.core.event.domain.Event
import xyz.avalonxr.models.EventClass
import xyz.avalonxr.models.ListenerFunction

abstract class EventListener {

    abstract fun getListeners(): Map<EventClass, ListenerFunction>

    inline fun <reified T : Event> Event.asType(): T? =
        this as? T
}
