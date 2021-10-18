package com.sophoun.ui_state.service

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.sophoun.utils.SdkUtils
import java.lang.Exception
import java.util.concurrent.atomic.AtomicReference

/**
 * ServiceManager
 * This class is use for control service binding
 * and provide some utilities method to control
 * service such as bind and unbind.
 *
 * It musts work with sub class of BaseService
 */
open class ServiceManager(private val context: Context) {
    @SuppressLint("HandlerLeak")
    inner class IncomingHandler : Handler() {
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            msg.let {
                _onMessageReceives.forEach { it.value.invoke(msg) }
            }
        }
    }

    private var _bound: Boolean = false
    private var _serviceMessenger: Messenger? = null
    private var _serviceManagerMessenger: Messenger? = null
    private val _onServiceConnected = AtomicReference<() -> Unit>().apply { set {  } }
    private val _onServiceDisconnected = AtomicReference<() -> Unit>().apply { set {  } }
    private val _onMessageReceives = hashMapOf<Int, (msg: Message) -> Unit>()

    private val _serviceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            _serviceMessenger = null
            _bound = false
            _onServiceDisconnected.get()?.invoke()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _serviceMessenger = Messenger(service)
            _serviceManagerMessenger = Messenger(IncomingHandler())
            _bound = true
            sendAddListener()
            _onServiceConnected.get()?.invoke()
        }
    }

    /**
     * Listen to service when it does connected
     */
    fun setOnServiceConnectedListener(listener: () -> Unit ) {
        _onServiceConnected.set { listener.invoke() }
    }

    /**
     * Listen to service when it does disconnected
     */
    open fun setOnServiceDisconnectedListener(listener: () -> Unit) {
        _onServiceDisconnected.set { listener.invoke() }
    }

    /**
     * Start a service.
     *
     * Note:
     * - must call startService before call bindService
     * @param clazz subclass of BaseService
     */
    fun <T: BaseService> startService(clazz: Class<T>) {
        context.startService(Intent(context, clazz))
    }

    /**
     * Start a service.
     *
     * Note:
     * - must call startService before call bindService
     * @param clazz subclass of BaseService
     */
    @SuppressLint("NewApi")
    fun <T: BaseService> startForegroundService(clazz: Class<T>) {
        SdkUtils.targetSdk(Build.VERSION_CODES.O, {
            context.startForegroundService(Intent(context, clazz))
        }, {
            startService(clazz)
        })
    }

    /**
     * Stop a service
     * @param clazz subclass of BaseService
     */
    fun <T: BaseService> stopService(clazz: Class<T>) {
        _onMessageReceives.clear()
        context.stopService(Intent(context, clazz))
    }

    /**
     * Bind service to current serviceConnection
     * @param clazz subclass of BaseService
     */
    fun <T: BaseService> bindService(clazz: Class<T>) {
        if (!_bound) {
            context.bindService(Intent(context, clazz), _serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    /**
     * Unbind current serviceConnection
     */
    fun unBind() {
        if (_bound) {
            sendRemoveListener()
            context.unbindService(_serviceConnection)
            _bound = false
        }
    }

    /**
     * Send handler to service to make service can send data back
     */
    private fun sendAddListener() {
        sendMessage(what = Keys.ADD_LISTENER, replyTo = _serviceManagerMessenger)
    }

    /**
     * Remove handler from service because we don't need to listen anymore
     */
    private fun sendRemoveListener() {
        sendMessage(what = Keys.REMOVE_LISTENER, replyTo = _serviceManagerMessenger)
    }

    /**
     * Send message to service
     * @param what Message key to past message.what
     * @param bundle Bundle that past to data object in message
     * @param replyTo Hold the messenger object to reply back
     */
    fun sendMessage(what: Int, bundle: Bundle? = null, replyTo: Messenger? = null) {
        try {
            _serviceMessenger?.send(Message.obtain().apply {
                this.what = what
                this.data = bundle
                this.replyTo = replyTo
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Add a message receive listener to service manager
     * @param onMessageReceiveListener
     */
    fun addOnMessageReceiveListener(onMessageReceiveListener: (msg: Message) -> Unit) {
        _onMessageReceives[onMessageReceiveListener.hashCode()] = onMessageReceiveListener
    }

    /**
     * Remove a message receive listener to service manager
     * @param onMessageReceiveListener
     */
    fun removeOnMessageReceiveListener(onMessageReceiveListener: (msg: Message) -> Unit) {
        _onMessageReceives.remove(onMessageReceiveListener.hashCode())
    }
}