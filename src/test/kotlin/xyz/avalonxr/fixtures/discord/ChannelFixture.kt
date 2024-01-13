package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.entity.channel.GuildChannel
import io.mockk.every
import io.mockk.mockk

@Suppress("LocalVariableName")
object ChannelFixture {

    fun makeGuildChannel(
        _name: String? = "bar",
        _mention: String? = "#bar",
    ): GuildChannel = mockk {
        every { name } returns _name
        every { mention } returns _mention
    }
}
