package xyz.avalonxr.config

import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import xyz.avalonxr.validation.validator.MockValidator

@Configuration
@ActiveProfiles("test")
class MockValidatorConfig {

    @Bean
    fun validatorOne(): MockValidator = mockk {
        every { name } returns "Validator 1"
        every { validate(any()) } returns null
    }

    @Bean
    fun validatorTwo(): MockValidator = mockk {
        every { name } returns "Validator 2"
        every { validate(any()) } returns null
    }

    @Bean
    fun validatorThree(): MockValidator = mockk {
        every { name } returns "Validator 3"
        every { validate(any()) } returns null
    }
}
