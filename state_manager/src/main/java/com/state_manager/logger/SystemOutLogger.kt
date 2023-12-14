package com.state_manager.logger

/**
 * An implementation of [Logger] which writes logs to [System.out]
 *
 * Logs are only written if logging is enabled.
 */
class SystemOutLogger(override val tag: String) : Logger {

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

fun systemOutLogger(tag: String = "ArcherState"): Logger = SystemOutLogger(tag)
