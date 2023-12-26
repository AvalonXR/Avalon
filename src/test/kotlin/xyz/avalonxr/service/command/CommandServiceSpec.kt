package xyz.avalonxr.service.command

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import xyz.avalonxr.data.CommandResult
import xyz.avalonxr.data.error.CommandError
import xyz.avalonxr.data.error.ValidationError
import xyz.avalonxr.fixtures.discord.ChatInputInteractionEventFixture
import xyz.avalonxr.handler.command.AvalonDefaultCommandHandler
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.models.discord.TestCommand
import xyz.avalonxr.service.LifecycleService

class CommandServiceSpec : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    describe("CommandService Tests") {
        val commands = mutableListOf(
            TestCommand
        )
        val validatorService = mockk<CommandValidationService> {
            every { validate(any()) } returns emptyList()
        }
        val lifecycleService = mockk<LifecycleService> {
            every { shutdown(any(), *anyVararg()) } throws RuntimeException("Exited")
        }
        val defaultHandler = AvalonDefaultCommandHandler()
        val event = ChatInputInteractionEventFixture.make()
        val failEvent = ChatInputInteractionEventFixture.make(
            _commandName = "bar"
        )
        val service = CommandService(
            commands,
            validatorService,
            lifecycleService,
            defaultHandler,
        )

        describe("processCommand") {
            lateinit var result: CommandResult

            describe("A command is executed successfully") {
                beforeTest {
                    result = service.processCommand(event)
                }

                it("Should be a success result") {
                    result.isError().shouldBeFalse()
                }
            }

            describe("When the command validation service returns an error") {
                beforeTest {
                    every { validatorService.validate(any()) } returns listOf(
                        ValidationError.DuplicateCommandNames(listOf("FOO"))
                    )
                    runCatching {
                        result = service.processCommand(event)
                    }
                }

                it("Should call lifecycle service shutdown") {
                    verify(exactly = 1) {
                        lifecycleService.shutdown(any(), *anyVararg())
                    }
                }
            }

            describe("When the requested command does not exist") {
                beforeTest {
                    result = service.processCommand(failEvent)
                }

                it("Should be a failure result") {
                    result.isError().shouldBeTrue()
                }

                it("Should have correct error message") {
                    result.data.shouldBeInstanceOf<CommandError.CommandNotFound>()
                }
            }
        }

        describe("findCommandByName") {
            var result: Command? = null

            describe("A command is retrieved successfully") {
                beforeTest {
                    result = service.findCommandByName("foo")
                }

                it("Should be a success result") {
                    result?.getBinding()!!.request.name() shouldBe "foo"
                }
            }

            describe("When the command validation service returns an error") {
                beforeTest {
                    every { validatorService.validate(any()) } returns listOf(
                        ValidationError.DuplicateCommandNames(listOf("FOO"))
                    )
                    runCatching {
                        result = service.findCommandByName("foo")
                    }
                }

                it("Should call lifecycle service shutdown") {
                    verify(exactly = 1) {
                        lifecycleService.shutdown(any(), *anyVararg())
                    }
                }
            }

            describe("When the requested command does not exist") {
                beforeTest {
                    result = service.findCommandByName("bar")
                }

                it("Should return null") {
                    result.shouldBeNull()
                }
            }
        }

        describe("findCommandsByNames") {
            lateinit var result: List<Command>

            describe("A list of commands is successfully retrieved") {
                beforeTest {
                    result = service.findCommandsByNames(listOf("foo"))
                }

                it("Should be a success result") {
                    result[0].getBinding().request.name() shouldBe "foo"
                }
            }

            describe("When the command validation service returns an error") {
                beforeTest {
                    every { validatorService.validate(any()) } returns listOf(
                        ValidationError.DuplicateCommandNames(listOf("foo"))
                    )
                    runCatching {
                        result = service.findCommandsByNames(listOf("foo"))
                    }
                }

                it("Should call lifecycle service shutdown") {
                    verify(exactly = 1) {
                        lifecycleService.shutdown(any(), *anyVararg())
                    }
                }
            }

            describe("When none of the requested commands exist") {
                beforeTest {
                    result = service.findCommandsByNames(listOf("bar"))
                }

                it("Should return an empty list") {
                    result.shouldBeEmpty()
                }
            }
        }

        describe("getAllCommands") {
            lateinit var result: List<Command>

            describe("All cached commands are successfully retrieved") {
                beforeTest {
                    result = service.getAllCommands()
                }

                it("Should be a success result") {
                    result[0].getBinding().request.name() shouldBe "foo"
                }
            }

            describe("When the command validation service returns an error") {
                beforeTest {
                    every { validatorService.validate(any()) } returns listOf(
                        ValidationError.DuplicateCommandNames(listOf("foo"))
                    )
                    runCatching {
                        result = service.getAllCommands()
                    }
                }

                it("Should call lifecycle service shutdown") {
                    verify(exactly = 1) {
                        lifecycleService.shutdown(any(), *anyVararg())
                    }
                }
            }
        }
    }
})
