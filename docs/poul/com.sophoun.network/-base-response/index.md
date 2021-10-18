[poul](../../index.md) / [com.sophoun.network](../index.md) / [BaseResponse](./index.md)

# BaseResponse

`data class BaseResponse<T>`

Base response class for network request.

### Types

| Name | Summary |
|---|---|
| [Info](-info/index.md) | `data class Info` |
| [Status](-status/index.md) | `enum class Status` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseResponse(meta: `[`BaseResponse.Info`](-info/index.md)`, data: `[`T`](index.md#T)`?)`<br>Base response class for network request. |

### Properties

| Name | Summary |
|---|---|
| [data](data.md) | `val data: `[`T`](index.md#T)`?` |
| [meta](meta.md) | `val meta: `[`BaseResponse.Info`](-info/index.md) |
