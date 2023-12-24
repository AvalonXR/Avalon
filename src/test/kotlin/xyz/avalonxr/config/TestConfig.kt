package xyz.avalonxr.config

import io.mockk.every
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import xyz.avalonxr.service.LifecycleService

@Configuration
class TestConfig {

    @Bean
    @Profile("test")
    fun mockLifecycleService(): LifecycleService = mockk {
        every { shutdown(any(), *anyVararg()) } throws RuntimeException("Exited.")
    }
}
