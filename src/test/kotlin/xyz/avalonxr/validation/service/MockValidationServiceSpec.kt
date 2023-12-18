package xyz.avalonxr.validation.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import xyz.avalonxr.validation.validator.MockValidator

@SpringBootTest
@ActiveProfiles("test")
class MockValidationServiceSpec @Autowired constructor(
    private val validationService: MockValidationService,
    @Qualifier("validatorTwo")
    private val validator: MockValidator
) : DescribeSpec({

    extensions(SpringTestExtension())

    describe("MockValidationService Tests") {

        describe("MockValidationService should be initialized") {

            it("Should have retrieved a bean of type MockValidationService") {
                validationService.shouldNotBeNull()
            }

            it("Should have 3 validators") {
                validationService.validators shouldHaveSize 3
            }

            it("Should validate successfully") {
                validationService.validate("Foo").shouldBeEmpty()
            }
        }

        describe("When one validator returns an error") {
            beforeTest {
                every { validator.validate(any()) } returns "Failure!"
            }

            it("Should return a list with one error") {
                validationService
                    .validate("Foo")
                    .shouldContainInOrder("Failure!")
            }
        }
    }
})
