[poul](../../index.md) / [com.sophoun.generic_adapter](../index.md) / [GenericViewHolder](./index.md)

# GenericViewHolder

`abstract class GenericViewHolder<T>`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `GenericViewHolder(parent: <ERROR CLASS>, layoutRes: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Functions

| Name | Summary |
|---|---|
| [bind](bind.md) | `open fun bind(data: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getLifecycle](get-lifecycle.md) | `open fun getLifecycle(): <ERROR CLASS>` |
| [onStateChanged](on-state-changed.md) | `open fun onStateChanged(state: `[`UiState`](../../com.sophoun.ui_state.state/-ui-state/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Client need to override this method to update their ui state |
| [setupWithViewModel](setup-with-view-model.md) | `fun setupWithViewModel(vararg viewModel: `[`BaseViewModel`](../../com.sophoun.ui_state/-base-view-model/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Attach ViewModel with UiState changed |
