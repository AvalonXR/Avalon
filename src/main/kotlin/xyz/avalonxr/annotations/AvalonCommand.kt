package xyz.avalonxr.annotations

import org.springframework.stereotype.Component
import xyz.avalonxr.annotations.dsl.CommandDsl

/**
 * @author Atri
 *
 * Describes a component stereotype relating to custom discord slash commands. Marking a class with this implicitly
 * scopes it as a subtype of the [Component] stereotype, which should allow spring to retrieve the value.
 */
@Component
@CommandDsl
@Retention(AnnotationRetention.RUNTIME)
annotation class AvalonCommand
