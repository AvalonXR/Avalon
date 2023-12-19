package xyz.avalonxr.handler.exit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import xyz.avalonxr.enums.ExitCode

class ExceptionOnErrorSpec : DescribeSpec({

    describe("ExceptionOnError Tests") {

        describe("When a success exit code is received") {
            val result = ExceptionOnError.handleExit(ExitCode.OK)

            it("Should return the exit code number") {
                result shouldBe 0
            }
        }

        describe("When an error exit code is received") {

            it("Should return the exit code number") {
                shouldThrow<IllegalStateException> {
                    ExceptionOnError.handleExit(ExitCode.INITIALIZATION_FAILURE)
                }
            }
        }
    }
})
