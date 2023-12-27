package xyz.avalonxr.repository

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.matchers.optional.shouldBeEmpty
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import xyz.avalonxr.data.entity.WelcomeMessage
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:sql/test/welcome_message_test.sql")
class WelcomeMessageRepositorySpec @Autowired constructor(
    private val repository: WelcomeMessageRepository,
) : DescribeSpec({

    extensions(SpringTestExtension())

    describe("WelcomeMessageRepository Tests") {

        describe("findById") {
            lateinit var result: Optional<WelcomeMessage>

            describe("A column is retrieved successfully") {
                beforeTest {
                    result = repository.findById(198123123)
                }

                it("Should have the correct message") {
                    result.shouldBePresent {
                        message shouldBe "Hello world!"
                    }
                }
            }
        }

        describe("findByGuildId") {
            lateinit var result: Optional<WelcomeMessage>

            describe("A column is retrieved successfully") {
                beforeTest {
                    result = repository.findByGuildId(1076316414421499924)
                }

                it("Should have the correct message") {
                    result.shouldBePresent {
                        message shouldBe "Hello world!"
                    }
                }
            }

            describe("When an guild id does not exist in the table") {
                result = repository.findByGuildId(98242)

                it("Should not return a result") {
                    result.shouldBeEmpty()
                }
            }
        }

        describe("save") {
            lateinit var result: Optional<WelcomeMessage>

            describe("A column is saved to the database successfully") {
                beforeTest {
                    repository.save(WelcomeMessage(9123912312, 21938123921, "Test!"))
                    result = repository.findByGuildId(9123912312)
                }

                it("Should have the correct message") {
                    result.shouldBePresent {
                        message shouldBe "Test!"
                    }
                }
            }

            describe("When an entry is saved over an existing entry") {

                it("Should result in a failure") {
                    shouldThrowAny {
                        repository.save(WelcomeMessage(9123912312, 21938123921, "Test!"))
                    }
                }
            }
        }
    }
})
