[poul](../../index.md) / [com.sophoun.query](../index.md) / [DeleteQueryBuilder](index.md) / [execute](./execute.md)

# execute

`fun execute(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Overrides [QueryBuilder.execute](../-query-builder/execute.md)

Execute the query builder. Result wills callback:

* `onResult(List<T>)`: after all mapped
* `onError(Exception)`: when something happens during execution
* `onCompleted()`: after all process completed (even error happen)
