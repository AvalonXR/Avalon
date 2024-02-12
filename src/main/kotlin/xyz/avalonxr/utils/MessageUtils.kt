package xyz.avalonxr.utils

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.entity.channel.Channel
import discord4j.core.`object`.entity.channel.TextChannel
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
 * @param message The message to send to the corresponding user.
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

/**
 * @author Atri
 *
 * A utility function which sends a text message to a given target channel. This automatically handles if the channel is
 * of an invalid type by ignoring channels which do not fit the criteria of a [TextChannel].
 *
 * @param message The message to send.
 * @param embeds Any embeds to link with the provided message.
 */
fun Channel.sendMessage(
    message: String,
    vararg embeds: EmbedCreateSpec
) {
    if (this !is TextChannel) {
        return
    }

    createMessage(message)
        .withEmbeds(*embeds)
        .subscribe()
}
