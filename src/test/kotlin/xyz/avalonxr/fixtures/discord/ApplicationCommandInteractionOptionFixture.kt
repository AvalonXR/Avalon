package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import io.mockk.every
import io.mockk.mockk
import java.util.Optional

@Suppress("LocalVariableName")
object ApplicationCommandInteractionOptionFixture {

    fun make(
        _name: String = "Baz",
        _value: ApplicationCommandInteractionOptionValue? = ApplicationCommandInteractionOptionValueFixture.make()
    ): ApplicationCommandInteractionOption = mockk {
        every { name } returns _name
        every { value } returns Optional.ofNullable(_value)
    }
}
