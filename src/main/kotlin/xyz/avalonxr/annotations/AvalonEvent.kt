package xyz.avalonxr.annotations

import org.springframework.stereotype.Component
import xyz.avalonxr.events.handler.EventHandler

/**
 * @author Atri
 *
 * Describes a component stereotype relating to classes which define event listener logic. These classes typically
 * should additionally implement some variant of [EventHandler].
 */
@Component
@Retention(AnnotationRetention.RUNTIME)
annotation class AvalonEvent
