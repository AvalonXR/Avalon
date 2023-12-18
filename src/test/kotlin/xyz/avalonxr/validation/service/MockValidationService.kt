package xyz.avalonxr.validation.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import xyz.avalonxr.validation.validator.MockValidator

@Service
@ActiveProfiles("test")
class MockValidationService @Autowired constructor(
    override val validators: List<MockValidator>
) : SingleValidationService<String, String, MockValidator>