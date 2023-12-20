package xyz.avalonxr.provider

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import xyz.avalonxr.enums.ExitStrategy
import xyz.avalonxr.handler.exit.DebugOnExit
import xyz.avalonxr.handler.exit.ExceptionOnError
import xyz.avalonxr.handler.exit.LogOnExit

class ExitStrategyHandleProviderSpec : DescribeSpec({

    fun makeSubject(
        strategy: ExitStrategy = ExitStrategy.LOG
    ): ExitStrategyHandleProvider = ExitStrategyHandleProvider(
        mockk {
            every { exitStrategy } returns strategy
        }
    )

    describe("ExitStrategyHandleProvider Tests") {

        describe("Provide") {

            describe("An exit strategy is retrieved") {
                val result = makeSubject()
                    .provide()

                it("Should return the correct strategy type") {
                    result shouldBe LogOnExit
                }
            }

            describe("When the exit strategy is set to EXCEPTION") {
                val result = makeSubject(ExitStrategy.EXCEPTION)
                    .provide()

                it("Should return the correct strategy type") {
                    result shouldBe ExceptionOnError
                }
            }

            describe("When the exit strategy is set to DEBUG") {
                val result = makeSubject(ExitStrategy.DEBUG)
                    .provide()

                it("Should return the correct strategy type") {
                    result shouldBe DebugOnExit
                }
            }
        }
    }
})
