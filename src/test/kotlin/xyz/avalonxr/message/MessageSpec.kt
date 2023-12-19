package xyz.avalonxr.message

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MessageSpec : DescribeSpec({

    describe("Message Tests") {

        describe("A message with no arguments") {
            val message = ExitMessage.ExitOk.format()

            it("Should return the correct message") {
                message shouldBe "Termination signal received, exit OK"
            }
        }

        describe("A message with no arguments receiving additional parameters") {
            val message = ExitMessage.ExitOk.format(1, "Foo", false)

            it("Should return the correct message") {
                message shouldBe "Termination signal received, exit OK"
            }
        }

        describe("A message with 2 arguments") {
            val message = ExitMessage.InitFailed.format("Foo", "Bar")

            it("Should return the correct message") {
                message shouldBe "Failed to initialize application at Foo phase: Bar"
            }
        }

        describe("A message with 2 arguments receiving no parameters") {
            val message = ExitMessage.InitFailed.format()

            it("Should return the correct message") {
                message shouldBe "Failed to initialize application at {0} phase: {1}"
            }
        }
    }
})
