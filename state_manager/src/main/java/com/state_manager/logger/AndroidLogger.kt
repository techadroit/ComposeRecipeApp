package com.state_manager.logger

import android.util.Log

/**
 * An implementation of [Logger] which writes out to the standard Android Log.
 *
 * Logs are only written if logging is enabled.
 */
internal class AndroidLogger(override val tag: String) : Logger {

    override fun log(message: String, level: Logger.Level) {
        if (!enableLogging) {
            return
        }
        when (level) {
            Logger.Level.DEBUG -> Log.d(tag, message)
            Logger.Level.VERBOSE -> Log.v(tag, message)
        }
    }
}

/**
 * A utility function to create instances of [AndroidLogger]
 */
fun androidLogger(tag: String): Logger = AndroidLogger(tag)
