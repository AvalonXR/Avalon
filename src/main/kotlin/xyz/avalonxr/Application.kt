package xyz.avalonxr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["xyz.avalonxr"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
