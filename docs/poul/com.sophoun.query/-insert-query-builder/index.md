[poul](../../index.md) / [com.sophoun.query](../index.md) / [InsertQueryBuilder](./index.md)

# InsertQueryBuilder

`class InsertQueryBuilder : `[`QueryBuilder`](../-query-builder/index.md)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?, `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<<ERROR CLASS>>, `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>`

This class can use for insert a list of data to
android database using ContentProvider

Sample usage:

```
InsertQueryBuilder<Album>(context?.contentResolver!!)
     .uri(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI)
     .mapper {
     arrayOf("p1", "p2", "p3", "p4", "p5")
         .map {
             ContentValues().apply { put(MediaStore.Audio.Playlists.NAME, it) }
         }
     }.onCompleted {
         DLog.i("Result", "Completed")
     }
     .onResult {
         DLog.i("Result", it.toString())
     }.onError {
         DLog.e("Result", it.toString())
     }.execute()
```

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `InsertQueryBuilder(contentResolver: <ERROR CLASS>)`<br>This class can use for insert a list of data to android database using ContentProvider |

### Inherited Properties

| Name | Summary |
|---|---|
| [contentResolver](../-query-builder/content-resolver.md) | `val contentResolver: <ERROR CLASS>` |
| [selection](../-query-builder/selection.md) | `var selection: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [selectionArgs](../-query-builder/selection-args.md) | `var selectionArgs: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<out `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?` |
| [uri](../-query-builder/uri.md) | `var uri: <ERROR CLASS>?` |

### Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | `fun execute(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Execute the query builder. Result wills callback: |

### Inherited Functions

| Name | Summary |
|---|---|
| [mapper](../-query-builder/mapper.md) | `fun mapper(mapper: (`[`I`](../-query-builder/index.md#I)`) -> `[`O`](../-query-builder/index.md#O)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>Map each object from cursor to T |
| [onCompleted](../-query-builder/on-completed.md) | `fun onCompleted(onCompleted: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>After all process completed |
| [onError](../-query-builder/on-error.md) | `fun onError(onError: (`[`Exception`](https://docs.oracle.com/javase/6/docs/api/java/lang/Exception.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>When something happen during process |
| [onResult](../-query-builder/on-result.md) | `fun onResult(onResult: (`[`T`](../-query-builder/index.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>After collected result |
| [selection](../-query-builder/selection.md) | `fun selection(selection: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>Selection condition |
| [selectionArgs](../-query-builder/selection-args.md) | `fun selectionArgs(vararg selectionArgs: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>Selection arguments |
| [uri](../-query-builder/uri.md) | `fun uri(uri: <ERROR CLASS>): `[`QueryBuilder`](../-query-builder/index.md)`<`[`I`](../-query-builder/index.md#I)`, `[`O`](../-query-builder/index.md#O)`, `[`T`](../-query-builder/index.md#T)`>`<br>Uri to query |
