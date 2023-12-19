package xyz.avalonxr.dsl.discord

import discord4j.core.spec.EmbedCreateFields
import discord4j.rest.util.Color
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeBlank

class EmbedDslSpec : DescribeSpec({

    describe("EmbedDsl Tests") {

        describe("A simple embed") {
            val embed = embed {
                title("You win!")
                color(Color.CYAN)
                description("Claim your prize!")
            }

            it("Should have the correct title") {
                embed.title().get() shouldBe "You win!"
            }

            it("Should have the correct color") {
                embed.color().get() shouldBe Color.CYAN
            }

            it("Should have the correct description") {
                embed.description().get() shouldBe "Claim your prize!"
            }
        }

        describe("An embed with a field group") {
            val embed = embed {
                title("Foo")

                fieldGroup {
                    field("A", "A")
                    field("B", "B")
                    field("C", "C")
                }
            }

            it("Should have the correct title") {
                embed.title().get() shouldBe "Foo"
            }

            it("Should have 4 fields (extra added whitespace field)") {
                embed.fields() shouldHaveSize 4
            }

            it("Should have correct field values") {
                embed.fields()
                    .map(EmbedCreateFields.Field::name)
                    .shouldContainInOrder("A", "B", "C")
                embed.fields()
                    .map(EmbedCreateFields.Field::value)
                    .shouldContainInOrder("A", "B", "C")
            }

            it("Should have a whitespace field at the end") {
                embed.fields()
                    .last()
                    .should {
                        it.name().shouldBeBlank()
                        it.value().shouldBeBlank()
                    }
            }
        }
    }
})
