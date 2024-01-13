package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.entity.Guild
import io.mockk.every
import io.mockk.mockk

@Suppress("LocalVariableName")
object GuildFixture {

    fun make(
        _name: String = "foo",
    ): Guild = mockk {
        every { name } returns _name
    }
}
