package xyz.avalonxr.service.command

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.avalonxr.data.error.ValidationError
import xyz.avalonxr.models.discord.Command
import xyz.avalonxr.validation.service.MultiValidationService
import xyz.avalonxr.validation.validator.command.CommandValidator

/**
 * @author Atri
 *
 * A validation service that is intended to validate the integrity and structure of [Command] objects. This is necessary
 * for us to perform ahead of pushing commands to Discord in order to ensure we do not send broken command definitions.
 *
 * @property validators All validators confirming to the type [CommandValidator]. This is provided to us by Spring.
 */
@Service
class CommandValidationService @Autowired constructor(
    override val validators: List<CommandValidator>
) : MultiValidationService<Command, ValidationError, CommandValidator> {

    override fun validate(values: List<Command>): List<ValidationError> {
        logger.info("Starting command validation...")
        val result = super.validate(values)
        logger.info("Commands processed")
        return result
    }

    companion object {

        private val logger = LoggerFactory
            .getLogger(CommandValidationService::class.java)
    }
}
