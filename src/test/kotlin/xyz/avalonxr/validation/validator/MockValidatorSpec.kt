package xyz.avalonxr.validation.validator

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import xyz.avalonxr.config.MockValidatorConfig

@SpringBootTest
@ActiveProfiles("test")
@Import(MockValidatorConfig::class)
class MockValidatorSpec @Autowired constructor(
    private val validators: List<MockValidator>
) : DescribeSpec({

    extensions(SpringTestExtension())

    describe("MockValidator Tests") {

        describe("Spring retrieves a list of validators matching type MockValidator") {

            it("Should pull back 3 classes of type MockValidator") {
                validators shouldHaveSize 3
            }

            it("Should have the proper validator names") {
                validators
                    .map(MockValidator::name)
                    .shouldContainInOrder("Validator 1", "Validator 2", "Validator 3")
            }
        }
    }
})
