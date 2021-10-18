package com.sophoun.ui.state.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * This class musts work with ServiceManager class
 * to make it easy while bind, unbind and handle message
 * receive from each other.
 */
abstract class BaseService : Service(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    @SuppressLint("HandlerLeak")
    inner class IncomingHandler: Handler() {
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            msg.let {
                when (it.what) {
                    Keys.ADD_LISTENER -> _replyToMessengers.add(it.replyTo)
                    Keys.REMOVE_LISTENER -> _replyToMessengers.remove(it.replyTo)
                    else -> onMessageReceive(it)
                }
            }
        }
    }

    private var _replyToMessengers = Collections.synchronizedList(mutableListOf<Messenger>())
    private val _messenger by lazy { Messenger(IncomingHandler()) }

    override fun onBind(intent: Intent?): IBinder? {
        return _messenger.binder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        job .cancel()
        super.onDestroy()
    }

    /**
     * Handle message that sent from ServiceManager
     */
    abstract fun onMessageReceive(msg: Message)

    /**
     * Send message to replyTo messengers
     * @param what Message key to past message.what
     * @param bundle Bundle that past to data object in message
     */
    fun sendMessage(what: Int, bundle: Bundle? = null) {
        try {
            _replyToMessengers.forEach { it.send(
                Message.obtain().apply {
                    this.what = what
                    this.data = bundle
                }
            )}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}