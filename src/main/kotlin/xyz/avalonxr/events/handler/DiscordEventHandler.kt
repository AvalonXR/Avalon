package xyz.avalonxr.events.handler

import discord4j.core.GatewayDiscordClient
import reactor.core.Disposable

/**
 * @author Atri
 *
 * Defines setup logic for all event functions associated with a class. Since Discord4J explicitly maps events via
 * client functions now, annotated function definitions are not possible. This approach allows us to define each
 * associated event as a private function and events using this.
 */
interface DiscordEventHandler : EventHandler {

    /**
     * Sets up all events associated with the implementer.
     *
     * @param client The Discord API client we are listening for events from.
     */
    fun setup(client: GatewayDiscordClient): Disposable
}
