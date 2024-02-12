package xyz.avalonxr.events

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.guild.MemberJoinEvent
import discord4j.core.`object`.entity.channel.GuildChannel
import org.slf4j.LoggerFactory
import reactor.core.Disposable
import reactor.core.publisher.Mono
import xyz.avalonxr.annotations.AvalonEvent
import xyz.avalonxr.command.WelcomeCommand
import xyz.avalonxr.data.entity.WelcomeMessage
import xyz.avalonxr.data.format.FormatContext
import xyz.avalonxr.events.handler.DiscordEventHandler
import xyz.avalonxr.repository.WelcomeMessageRepository
import xyz.avalonxr.service.FormatService
import xyz.avalonxr.utils.sendMessage

/**
 * @author Atri
 *
 * Event class making up the other half of [WelcomeCommand]. This will be responsible for alerting users in the guild
 * to the introduction of a new member.
 */
@AvalonEvent
class WelcomeEvent(
    private val welcomeMessageRepository: WelcomeMessageRepository,
    private val formatService: FormatService,
) : DiscordEventHandler {

    override fun setup(client: GatewayDiscordClient): Disposable = client
        .on(MemberJoinEvent::class.java, ::onMemberJoin)
        .subscribe()

    private fun onMemberJoin(event: MemberJoinEvent): Mono<Any> {
        val guildId = event.guildId.asLong()
        val welcome = welcomeMessageRepository
            .findByGuildId(guildId)
            .orElse(null)

        if (welcome == null) {
            logger.debug("Welcome message not configured for Guild ID '$guildId'")
            return Mono.empty()
        }

        return event.guild
            .flatMap { it.getChannelById(Snowflake.of(welcome.channelId)) }
            .map { sendMessage(it, event, welcome) }
    }

    private fun sendMessage(channel: GuildChannel, event: MemberJoinEvent, welcomeMessage: WelcomeMessage) {
        val context = FormatContext(
            guild = event.guild,
            channel = Mono.just(channel),
            member = Mono.just(event.member),
            user = Mono.just(event.member),
        )
        val message = formatService
            .formatWithContext(context, welcomeMessage.message)
        // Broadcast message to the provided channel
        channel.sendMessage(message)
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(WelcomeEvent::class.java)
    }
}
