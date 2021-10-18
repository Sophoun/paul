[poul](../../index.md) / [com.sophoun.utils](../index.md) / [ScopeStorageHandler](./index.md)

# ScopeStorageHandler

`class ScopeStorageHandler`

A Utility class that help you easy to access android scope storage.
It's require android Q or above.
This class is require when you want to update or delete a file or group of files
using [ContentResolver](#) so [ContentResolver](#) will throw [RecoverableSecurityException](#)
if you doesn't have permission on those files.
So after [ContentResolver](#) throw exception, you can use this call to request those permission
and after user allow or deny it wills callback to [onResult](#) method.
Note:

* To use this class you need to create an instance of it once and use in your activity.
* You must call [handleRequestIntent](handle-request-intent.md) in [onActivityResult](#) to handle callback result.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ScopeStorageHandler(activity: <ERROR CLASS>)`<br>A Utility class that help you easy to access android scope storage. It's require android Q or above. This class is require when you want to update or delete a file or group of files using [ContentResolver](#) so [ContentResolver](#) will throw [RecoverableSecurityException](#) if you doesn't have permission on those files. So after [ContentResolver](#) throw exception, you can use this call to request those permission and after user allow or deny it wills callback to [onResult](#) method. Note: |

### Functions

| Name | Summary |
|---|---|
| [handleRequestIntent](handle-request-intent.md) | `fun handleRequestIntent(requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, resultCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Call this method inside [onActivityResult](#) to handle callback result of [sendRequestIntent](send-request-intent.md) |
| [sendRequestIntent](send-request-intent.md) | `fun sendRequestIntent(intentSender: <ERROR CLASS>, requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, onResult: (success: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Request permission for android scope storage |
