[poul](../../index.md) / [com.sophoun.ui_state.service](../index.md) / [ServiceManager](index.md) / [startForegroundService](./start-foreground-service.md)

# startForegroundService

`fun <T : `[`BaseService`](../-base-service/index.md)`> startForegroundService(clazz: `[`Class`](https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<`[`T`](start-foreground-service.md#T)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Start a service.

Note:

* must call startService before call bindService

### Parameters

`clazz` - subclass of BaseService