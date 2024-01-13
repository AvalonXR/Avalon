package xyz.avalonxr.fixtures.data

import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.User
import discord4j.core.`object`.entity.channel.Channel
import reactor.core.publisher.Mono
import xyz.avalonxr.data.format.FormatContext
import xyz.avalonxr.fixtures.discord.ChannelFixture
import xyz.avalonxr.fixtures.discord.GuildFixture
import xyz.avalonxr.fixtures.discord.UserFixture

@Suppress("LocalVariableName")
object FormatContextFixture {

    fun make(
        _guild: Guild? = GuildFixture.make(),
        _channel: Channel? = ChannelFixture.makeGuildChannel(),
        _user: User? = UserFixture.make(),
        _member: Member? = null,
    ): FormatContext = FormatContext(
        guild = Mono.justOrEmpty(_guild),
        channel = _channel
            ?.let { Mono.just(it) }
            ?: Mono.empty(),
        user = Mono.justOrEmpty(_user),
        member = Mono.justOrEmpty(_member),
    )
}
