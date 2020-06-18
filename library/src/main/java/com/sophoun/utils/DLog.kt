package com.sophoun.utils

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.sophoun.utils.DLog.Level.DEBUG
import com.sophoun.utils.DLog.Level.ERROR
import com.sophoun.utils.DLog.Level.INFO
import com.sophoun.utils.DLog.Level.NONE
import com.sophoun.utils.DLog.LogType.D
import com.sophoun.utils.DLog.LogType.E
import com.sophoun.utils.DLog.LogType.I
import com.sophoun.utils.DLog.LogType.JSON
import com.sophoun.utils.DLog.LogType.V
import com.sophoun.utils.DLog.LogType.W
import com.sophoun.utils.DLog.LogType.WTF
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

/**
 * Custom logger class.
 *
 * It's log depend on condition that we're provided.
 * (default: it's log only on debug mode)
 */

object DLog {
    private lateinit var logLevel: Level
    private const val JSON_INDENT = 4
    private val handlerThread = HandlerThread("dLogThread").also { it.start() }
    private val handler = Handler(handlerThread.looper)

    private enum class LogType {
        I, V, D, W, E, WTF, JSON
    }

    enum class Level {
        NONE, INFO, DEBUG, ERROR
    }

    /**
     * Enable log function.
     * - `NONE`: doesn't show log information
     * - `INFO`: log only I, V, JSON
     * - `DEBUG`: log INFO plus D
     * - `ERROR`: long all
     * Note:
     *  - Default [ERROR]
     *  - Initialize only one time in application class
     */
    fun level(level: Level) {
        if (DLog::logLevel.isInitialized) {
            throw Exception("Log level is already initialized!")
        }
        logLevel = level
    }

    @Synchronized private fun logByLevel(type: LogType, tag: String?, msg: String) {
        if (!DLog::logLevel.isInitialized) {
            throw Exception("Log Level is not yet initialize! You need to be initialize log level in the application class.")
        }
        when (logLevel) {
            NONE -> return
            INFO -> {
                if (type > V) return
                log(type, tag, msg)
            }
            DEBUG -> {
                if (type > W) return
                log(type, tag, msg)
            }
            ERROR -> log(type, tag, msg)
        }
    }

    @Synchronized private fun log(type: LogType, tag: String?, msg: String) {
        val threadName = Thread.currentThread().name
        val callerClass = Thread.currentThread().stackTrace?.get(4)?.className?.substringAfterLast(".")
        val threadInfo = "Thread: $threadName ->: "
        val msgPrint = threadInfo + msg
        val printTag = tag ?: callerClass ?: ""
        handler.post {
            when (type) {
                I -> Log.i(printTag, msgPrint)
                V -> Log.v(printTag, msgPrint)
                D -> Log.d(printTag, msgPrint)
                W -> Log.w(printTag, msgPrint)
                E -> Log.e(printTag, msgPrint)
                WTF -> Log.wtf(printTag, msgPrint)
                JSON -> Log.d(printTag, msgPrint)
            }
        }
    }

    fun i(msg: String, vararg args: Any) {
        logByLevel(I, null, formatMsg(msg, args))
    }

    fun v(msg: String, vararg args: Any) {
        logByLevel(V, null, formatMsg(msg, args))
    }

    fun d(msg: String, vararg args: Any) {
        logByLevel(D, null, formatMsg(msg, args))
    }

    fun e(msg: String, vararg args: Any) {
        logByLevel(E, null, formatMsg(msg, args))
    }

    fun w(msg: String, vararg args: Any) {
        logByLevel(W, null, formatMsg(msg, args))
    }

    fun wtf(msg: String, vararg args: Any) {
        logByLevel(WTF, null, formatMsg(msg, args))
    }

    fun json(tag: String, jsonString: String) {
        logByLevel(JSON, tag, getJsonFormat(jsonString))
    }

    private fun formatMsg(msg: String, args: Array<out Any>): String {
        return msg.format(*args)
    }

    private fun getJsonFormat(jsonString: String): String {
        if (jsonString.isEmpty()) {
            return "Empty/Null getJsonFormat content"
        }
        try {
            val json = jsonString.trim()
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                return jsonObject.toString(JSON_INDENT)
            }
            if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                return jsonArray.toString(JSON_INDENT)
            }
            return "Invalid Json"
        } catch (e: JSONException) {
            return "Invalid Json"
        }
    }
}
