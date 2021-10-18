package com.sophoun.query

import android.content.ContentResolver
import android.net.Uri

/**
 * This is a super class that provide functionality to sub class
 * to make it easy use contentResolver to query android database
 * @param I -> type of value that mapper function receive
 * @param O -> type of value that mapper function return
 * @param T -> type of value that pass to onResult(T) function
 */
abstract class QueryBuilder<I, O, T>(val contentResolver: ContentResolver) {

    var uri: Uri? = null
    var selection: String? =  null
    var selectionArgs: Array<out String>? =  null

    private var mapper: ((I) -> O)? = null

    private var onResult: ((T) -> Unit)? = null
    private var onCompleted: (() -> Unit)? = null
    private var onError: ((Exception) -> Unit)? = null

    /**
     * Builder function
     */

    /**
     * Uri to query
     */
    fun uri(uri: Uri): QueryBuilder<I, O, T> {
        this.uri = uri
        return this
    }

    /**
     * Selection condition
     */
    fun selection(selection: String): QueryBuilder<I, O, T> {
        this.selection = selection
        return this
    }

    /**
     * Selection arguments
     */
    fun selectionArgs(vararg selectionArgs: String): QueryBuilder<I, O, T> {
        this.selectionArgs = selectionArgs
        return this
    }

    /**
     * Map each object from cursor to T
     */
    fun mapper(mapper: (I) -> O): QueryBuilder<I, O, T> {
        this.mapper = mapper
        return this
    }

    /**
     * After collected result
     */
    fun onResult(onResult: (T) -> Unit): QueryBuilder<I, O, T> {
        this.onResult = onResult
        return this
    }

    /**
     * After all process completed
     */
    fun onCompleted(onCompleted: () -> Unit): QueryBuilder<I, O, T> {
        this.onCompleted = onCompleted
        return this
    }

    /**
     * When something happen during process
     */
    fun onError(onError: (Exception) -> Unit): QueryBuilder<I, O, T> {
        this.onError = onError
        return this
    }

    /**
     * Execute the query builder. Result wills callback:
     * - `onResult(List<T>)`: after all mapped
     * - `onError(Exception)`: when something happens during execution
     * - `onCompleted()`: after all process completed (even error happen)
     */
    abstract fun execute()

    /**
     * On each value map
     */
    internal fun map(input: I): O? {
        return mapper?.invoke(input)
    }

    /**
     * After collected result
     */
    internal fun onResult(result: T) {
        this.onResult?.invoke(result)
    }

    /**
     * When something goes during process
     */
    internal fun onError(e: Exception){
        onError?.invoke(e)
    }

    /**
     * After all process completed
     */
    internal fun onCompleted() {
        this.onCompleted?.invoke()
    }
}