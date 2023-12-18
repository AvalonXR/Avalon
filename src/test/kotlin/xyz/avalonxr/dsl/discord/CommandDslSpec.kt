package xyz.avalonxr.dsl.discord

import discord4j.discordjson.json.ApplicationCommandOptionChoiceData
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.optional.shouldBeEmpty
import io.kotest.matchers.shouldBe

class CommandDslSpec : DescribeSpec({

    describe("CommandDsl Tests") {

        describe("Command with one optional input") {
            val command = command("Foo Bar") {
                description("Hello world!")

                option(OptionType.STRING) {
                    name("Test")
                    description("Enter text here")
                    optional()
                }
            }

            it("Should have the correct name") {
                command.name() shouldBe "Foo Bar"
            }

            it("Should have the correct description") {
                command.description().get() shouldBe "Hello world!"
            }

            it("Should have one option") {
                command.options().get() shouldHaveSize 1
            }

            it("Should have the correct option type") {
                command.options().get().first().type() shouldBe OptionType.STRING.value
            }

            it("Should have the correct option name") {
                command.options().get().first().name() shouldBe "Test"
            }

            it("Should have the correct option description") {
                command.options().get().first().description() shouldBe "Enter text here"
            }

            it("Should not have a required option") {
                command.options().get().shouldForAll {
                    it.required().get().shouldBeFalse()
                }
            }
        }

        describe("Command with multiple options") {
            val command = command("Foo Bar") {
                description("Hello world!")

                option(OptionType.STRING) {
                    name("Test")
                    description("Enter text here")
                    optional()
                }

                option(OptionType.UNKNOWN) {
                    name("Test 2")
                    description("Enter whatever here?")
                }

                option(OptionType.USER) {
                    name("Test 3")
                    description("Enter some user here")
                }
            }

            it("Should have the correct name") {
                command.name() shouldBe "Foo Bar"
            }

            it("Should have the correct description") {
                command.description().get() shouldBe "Hello world!"
            }

            it("Should have one option") {
                command.options().get() shouldHaveSize 3
            }

            it("Should have the correct option type") {
                command.options().get()[0].type() shouldBe OptionType.STRING.value
                command.options().get()[1].type() shouldBe OptionType.UNKNOWN.value
                command.options().get()[2].type() shouldBe OptionType.USER.value
            }

            it("Should have the correct option name") {
                command.options().get()[0].name() shouldBe "Test"
                command.options().get()[1].name() shouldBe "Test 2"
                command.options().get()[2].name() shouldBe "Test 3"
            }

            it("Should have the correct option description") {
                command.options().get()[0].description() shouldBe "Enter text here"
                command.options().get()[1].description() shouldBe "Enter whatever here?"
                command.options().get()[2].description() shouldBe "Enter some user here"
            }

            it("Should have correct required options") {
                command.options().get()[0].required().get().shouldBeFalse()
                command.options().get()[1].required().get().shouldBeTrue()
                command.options().get()[2].required().get().shouldBeTrue()
            }
        }

        describe("Empty command") {
            val command = command("foo")

            it("Should have the correct name") {
                command.name() shouldBe "foo"
            }

            it("Should have the correct description") {
                command.description().toOptional().shouldBeEmpty()
            }

            it("Should have one option") {
                command.options().toOptional().shouldBeEmpty()
            }
        }

        describe("Command with option type choices set") {
            val command = command("foo") {
                option(OptionType.STRING) {
                    name("text")
                    description("fill this in!")
                    choices<Example>()
                }
            }

            it("Should have the correct name") {
                command.name() shouldBe "foo"
            }

            it("Should have one option") {
                command.options().get() shouldHaveSize 1
            }

            it("Should have the correct option type") {
                command.options().get().first().type() shouldBe OptionType.STRING.value
            }

            it("Should have the correct option name") {
                command.options().get().first().name() shouldBe "text"
            }

            it("Should have the correct option description") {
                command.options().get().first().description() shouldBe "fill this in!"
            }

            it("Should have the correct choices") {
                command.options().get().first().choices().get()
                    .map(ApplicationCommandOptionChoiceData::name)
                    .shouldContainInOrder(Example.entries.map { it.name.formatCapitalCase() })
                command.options().get().first().choices().get()
                    .map(ApplicationCommandOptionChoiceData::value)
                    .shouldContainInOrder(Example.entries.map(Example::name))
            }
        }
    }
})

private enum class Example {

    TEST_FOO,

    TEST_BAR,

    TEST_BAZ,
}