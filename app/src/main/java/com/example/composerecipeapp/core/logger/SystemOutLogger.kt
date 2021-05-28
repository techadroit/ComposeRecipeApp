package com.example.composerecipeapp.core.logger

var enableLogging:Boolean = false

/**
 * An implementation of [Logger] which writes logs to [System.out]
 *
 * Logs are only written if logging is enabled.
 */
internal class SystemOutLogger(override val tag: String) : Logger {

    override fun log(message: String, level: Logger.Level) {
        if (!enableLogging) {
            return
        }
        when (level) {
            Logger.Level.DEBUG -> println("D/$tag: $message")
            Logger.Level.VERBOSE -> println("V/$tag: $message")
        }
    }
}

fun systemOutLogger(tag: String = "AppState"): Logger = SystemOutLogger(tag)
