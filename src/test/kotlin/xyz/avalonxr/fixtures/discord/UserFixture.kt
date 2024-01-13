package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.entity.User
import io.mockk.every
import io.mockk.mockk

@Suppress("LocalVariableName")
object UserFixture {

    fun make(
        _username: String = "FooBar",
        _mention: String = "@FooBar"
    ): User = mockk {
        every { username } returns _username
        every { mention } returns _mention
    }
}
