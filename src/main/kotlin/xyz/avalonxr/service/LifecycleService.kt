package xyz.avalonxr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import xyz.avalonxr.enums.ExitCode
import xyz.avalonxr.enums.ExitStrategy
import xyz.avalonxr.provider.ExitStrategyHandleProvider
import kotlin.system.exitProcess

/**
 * @author Atri
 *
 * A simple service meant to encapsulate calls to [SpringApplication.exit]. This can be used to shut down the
 * application gracefully, or abruptly in the event that a fatal error occurs or invalid state is reached.
 *
 * @property context The spring context for the application we want to terminate.
 * @property strategy The exit strategy to use before terminating the application. Can be of the following types: LOG,
 * EXCEPTION, or DEBUG.
 */
@Service
class LifecycleService @Autowired constructor(
    private val context: ApplicationContext,
    private val strategy: ExitStrategyHandleProvider
) {

    /**
     * Shuts down the application and calls the provided [ExitStrategy].
     *
     * @param code The exit code to set our shutdown by.
     * @param extras Any values that we would like to include in our exit code's log message.
     */
    fun shutdown(
        code: ExitCode = ExitCode.OK,
        vararg extras: Any?
    ): Nothing = SpringApplication
        .exit(context, { strategy.provide().handleExit(code, *extras) })
        .let { exitProcess(code.code) }
}
