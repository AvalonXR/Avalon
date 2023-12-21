package xyz.avalonxr.fixtures.discord

import discord4j.common.util.Snowflake
import discord4j.core.`object`.command.Interaction
import discord4j.core.`object`.entity.User
import io.mockk.every
import io.mockk.mockk
import java.util.*

@Suppress("LocalVariableName")
object InteractionFixture {

    fun make(
        _user: User = UserFixture.make(),
        _guildId: Long? = 329438234823423423,
    ): Interaction = mockk {
        every { user } returns _user
        every { guildId } returns _guildId
            ?.let(Snowflake::of)
            .let { Optional.ofNullable(it) }
    }
}
