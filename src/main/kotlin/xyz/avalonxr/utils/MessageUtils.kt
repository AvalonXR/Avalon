package xyz.avalonxr.utils

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec
import reactor.core.publisher.Mono

/**
 * @author Atri
 *
 * A utility function which allows us to easily reply to messages with an embed, rather than requiring callers to run
 * through the underlying builder classes directly. This saves us time on needing to write and maintain largely
 * redundant code.
 *
 * @param embed The constructed embed we would like to reply with.
 * @param ephemeral Whether the embed given is only visible to the event caller or everyone.
 */
fun ChatInputInteractionEvent.replyWithEmbed(
    embed: EmbedCreateSpec,
    ephemeral: Boolean = true,
): Mono<Void> = InteractionApplicationCommandCallbackSpec
    .create()
    .withEmbeds(embed)
    .withEphemeral(ephemeral)
    .let(::reply)

/**
 * @author Atri
 *
 * A utility function that sends a reply to the sender of a command.
 *
 * @param message The message to send to the corresponding user
 * @param ephemeral Whether the embed given is only visible to the event caller or everyone.
 */
fun ChatInputInteractionEvent.sendReply(
    message: String,
    ephemeral: Boolean = true,
) {
    reply(message)
        .withEphemeral(ephemeral)
        .onErrorComplete()
        .subscribe()
}
