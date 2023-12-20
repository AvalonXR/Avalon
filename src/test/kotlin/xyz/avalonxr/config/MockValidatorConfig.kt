package xyz.avalonxr.config

import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import xyz.avalonxr.enums.ExitStrategy
import xyz.avalonxr.validation.validator.MockValidator

@Configuration
@ActiveProfiles("test")
class MockValidatorConfig @Autowired constructor(
    private val appSettings: AppSettings
) {

    @Bean
    fun exitStrategy(): ExitStrategy =
        appSettings.exitStrategy

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
