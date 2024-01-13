package xyz.avalonxr.data.format

import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.Channel
import reactor.core.publisher.Mono

/**
 * @author Atri
 *
 * This object encapsulates any items that may be associated with a given message context. This functions effectively
 * as a wrapper to provide bindings to functions which may or may not provide the necessary data for a formatting step.
 * To ensure performance, objects are provided in [Mono] boxing to ensure data is only retrieved as required.
 *
 * @property guild The originating guild associated with the action or event.
 * @property channel The originating channel associated with the action or event.
 * @property user The guild member data associated to the given [user].
 * @property user The user who this context relates to.
 */
data class FormatContext(
    val guild: Mono<Guild> = Mono.empty(),
    val channel: Mono<out Channel> = Mono.empty(),
    val member: Mono<Member> = Mono.empty(),
    val user: Mono<User> = Mono.empty(),
)
