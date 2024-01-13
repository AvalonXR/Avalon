package xyz.avalonxr.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import xyz.avalonxr.data.format.FormatContext
import xyz.avalonxr.fixtures.data.FormatContextFixture

@SpringBootTest
class FormatServiceSpec(
    private val formatService: FormatService,
) : DescribeSpec({

    extensions(SpringTestExtension())

    describe("FormatService Tests") {

        describe("formatWithContext") {

            describe("Simple format") {
                val context = FormatContextFixture.make()
                val format = "Hello \${username.username}!"

                it("Should format properly") {
                    formatService
                        .formatWithContext(context, format)
                        .shouldBe("Hello FooBar!")
                }
            }

            describe("Longer format with channel and guild") {
                val context = FormatContextFixture.make()
                val format = "Hello \${username.username}! Welcome to \${guild}, you are in \${channel}"

                it("Should format properly") {
                    formatService
                        .formatWithContext(context, format)
                        .shouldBe("Hello FooBar! Welcome to foo, you are in #bar")
                }
            }

            describe("Empty context") {
                val context = FormatContext()
                val format = "Hello \${username.username}! Welcome to \${guild}, you are in \${channel}"
                val expected = "Hello [Invalid username]! Welcome to [Invalid guild], you are in [Invalid channel]"

                it("Should format properly") {
                    formatService
                        .formatWithContext(context, format)
                        .shouldBe(expected)
                }
            }
        }
    }
})
