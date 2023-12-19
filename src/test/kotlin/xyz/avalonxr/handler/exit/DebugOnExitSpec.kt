package xyz.avalonxr.handler.exit

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import xyz.avalonxr.enums.ExitCode

class DebugOnExitSpec : DescribeSpec({

    describe("DebugOnExit Tests") {

        describe("When a success exit code is received") {
            val result = DebugOnExit.handleExit(ExitCode.OK)

            it("Should return the exit code number") {
                result shouldBe 0
            }
        }

        describe("When an error exit code is received") {
            val result = DebugOnExit.handleExit(ExitCode.INITIALIZATION_FAILURE)

            it("Should return the exit code number") {
                result shouldBe 1
            }
        }
    }
})
