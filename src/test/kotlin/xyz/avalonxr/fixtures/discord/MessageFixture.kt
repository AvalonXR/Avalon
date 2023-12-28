package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.entity.Message
import io.mockk.every
import io.mockk.mockk

@Suppress("LocalVariableName")
object MessageFixture {

    fun make(
        _content: String = "Hello world!"
    ): Message = mockk {
        every { content } returns _content
    }
}
