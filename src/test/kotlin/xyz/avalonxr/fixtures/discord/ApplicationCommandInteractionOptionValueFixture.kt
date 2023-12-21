package xyz.avalonxr.fixtures.discord

import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import io.mockk.mockk
import xyz.avalonxr.dsl.discord.OptionType

@Suppress("LocalVariableName")
object ApplicationCommandInteractionOptionValueFixture {

    fun make(
        _guildId: Long? = 2931823482343243242,
        _optionType: OptionType = OptionType.STRING,
        _value: String = "FooBarBaz"
    ): ApplicationCommandInteractionOptionValue = ApplicationCommandInteractionOptionValue(
        mockk(),
        _guildId,
        _optionType.value,
        _value,
        mockk()
    )
}
