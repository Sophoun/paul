[poul](../index.md) / [com.sophoun.utils](./index.md)

## Package com.sophoun.utils

### Types

| Name | Summary |
|---|---|
| [BiometricManager](-biometric-manager/index.md) | `class BiometricManager` |
| [DLog](-d-log/index.md) | `object DLog`<br>Custom logger class. |
| [EncryptDecryptUtils](-encrypt-decrypt-utils/index.md) | `class EncryptDecryptUtils` |
| [FirebaseAnalyticsManager](-firebase-analytics-manager/index.md) | `class FirebaseAnalyticsManager` |
| [LocationWatcher](-location-watcher/index.md) | `class LocationWatcher`<br>LocationWatcher is a class that make us easier to listen to location update and check GPS is enable or not and it's also easy to get last know location. |
| [NetworkUtils](-network-utils/index.md) | `object NetworkUtils` |
| [ScopeStorageHandler](-scope-storage-handler/index.md) | `class ScopeStorageHandler`<br>A Utility class that help you easy to access android scope storage. It's require android Q or above. This class is require when you want to update or delete a file or group of files using [ContentResolver](#) so [ContentResolver](#) will throw [RecoverableSecurityException](#) if you doesn't have permission on those files. So after [ContentResolver](#) throw exception, you can use this call to request those permission and after user allow or deny it wills callback to [onResult](#) method. Note: |
| [SdkUtils](-sdk-utils/index.md) | `object SdkUtils` |
| [SharedPreferencesManager](-shared-preferences-manager/index.md) | `open class SharedPreferencesManager` |
| [SingleLiveEvent](-single-live-event/index.md) | `class SingleLiveEvent<T>`<br>This avoids a common problem with events: on configuration change (like rotation) an update can be emitted if the observer is active. This LiveData only calls the observable if there's an explicit call to `postValue()` or `setValue()`. |
| [SoftKeyboard](-soft-keyboard/index.md) | `object SoftKeyboard` |

### Functions

| Name | Summary |
|---|---|
| [applyExtras](apply-extras.md) | `fun <ERROR CLASS>.applyExtras(extras: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [navigateTo](navigate-to.md) | `fun <T> <ERROR CLASS>.navigateTo(isRoot: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, extras: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> = emptyMap()): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
