package xyz.avalonxr.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.avalonxr.config.AppSettings
import xyz.avalonxr.enums.ExitStrategy
import xyz.avalonxr.handler.exit.ApplicationExitHandler
import xyz.avalonxr.handler.exit.DebugOnExit
import xyz.avalonxr.handler.exit.ExceptionOnError
import xyz.avalonxr.handler.exit.LogOnExit

@Component
class ExitStrategyHandleProvider @Autowired constructor(
    private val appSettings: AppSettings
) : Provider<ApplicationExitHandler> {

    override fun provide(): ApplicationExitHandler = when (appSettings.exitStrategy) {
        ExitStrategy.LOG -> LogOnExit
        ExitStrategy.EXCEPTION -> ExceptionOnError
        ExitStrategy.DEBUG -> DebugOnExit
    }
}
