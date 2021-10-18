[poul](../../index.md) / [com.sophoun.ui_state.service](../index.md) / [ServiceManager](./index.md)

# ServiceManager

`open class ServiceManager`

ServiceManager
This class is use for control service binding
and provide some utilities method to control
service such as bind and unbind.

It musts work with sub class of BaseService

### Types

| Name | Summary |
|---|---|
| [IncomingHandler](-incoming-handler/index.md) | `inner class IncomingHandler` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ServiceManager(context: <ERROR CLASS>)`<br>ServiceManager This class is use for control service binding and provide some utilities method to control service such as bind and unbind. |

### Functions

| Name | Summary |
|---|---|
| [addOnMessageReceiveListener](add-on-message-receive-listener.md) | `fun addOnMessageReceiveListener(onMessageReceiveListener: (<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Add a message receive listener to service manager |
| [bindService](bind-service.md) | `fun <T : `[`BaseService`](../-base-service/index.md)`> bindService(clazz: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`T`](bind-service.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Bind service to current serviceConnection |
| [removeOnMessageReceiveListener](remove-on-message-receive-listener.md) | `fun removeOnMessageReceiveListener(onMessageReceiveListener: (<ERROR CLASS>) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Remove a message receive listener to service manager |
| [sendMessage](send-message.md) | `fun sendMessage(what: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, bundle: <ERROR CLASS>? = null, replyTo: <ERROR CLASS>? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Send message to service |
| [setOnServiceConnectedListener](set-on-service-connected-listener.md) | `fun setOnServiceConnectedListener(listener: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Listen to service when it does connected |
| [setOnServiceDisconnectedListener](set-on-service-disconnected-listener.md) | `open fun setOnServiceDisconnectedListener(listener: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Listen to service when it does disconnected |
| [startForegroundService](start-foreground-service.md) | `fun <T : `[`BaseService`](../-base-service/index.md)`> startForegroundService(clazz: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`T`](start-foreground-service.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Start a service. |
| [startService](start-service.md) | `fun <T : `[`BaseService`](../-base-service/index.md)`> startService(clazz: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`T`](start-service.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Start a service. |
| [stopService](stop-service.md) | `fun <T : `[`BaseService`](../-base-service/index.md)`> stopService(clazz: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`T`](stop-service.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Stop a service |
| [unBind](un-bind.md) | `fun unBind(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Unbind current serviceConnection |
