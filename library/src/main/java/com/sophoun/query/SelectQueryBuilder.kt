package com.sophoun.query

import android.content.ContentResolver
import android.database.Cursor

/**
 * This class use for query data from android database
 * using ContentProvider
 *
 * Sample usage:
 * ```
 * SelectQueryBuilder<Album>(context?.contentResolver!!)
 *       .projection(
 *          MediaStore.Audio.Playlists._ID,
 *          MediaStore.Audio.Playlists.NAME
 *       ).orderBy("${MediaStore.Audio.Playlists.NAME} ASC")
 *       .uri(MediaStore.Audio.Playlists.Members.getContentUri("external", 2818))
 *       .mapper {
 *          Album(
 *              it.getInt(it.getColumnIndex(MediaStore.Audio.Playlists._ID)),
 *              it.getString(it.getColumnIndex(MediaStore.Audio.Playlists.NAME))
 *          )
 *       }.onResult {
 *          DLog.i("Result", it.distinct().toString())
 *       }.onError {
 *          DLog.i("Result", it.message.toString())
 *       }.execute()
 * ```
 */

class SelectQueryBuilder<T>(contentResolver: ContentResolver): QueryBuilder<Cursor, T, List<T>>(contentResolver) {

    private var projection: Array<out String>? =  null
    private var orderBy: String? =  null

    /**
     * Builder function
     */

    /**
     * selection fields
     */
    fun projection(vararg projection: String): SelectQueryBuilder<T> {
        this.projection = projection
        return this
    }

    /**
     * order by condition
     */
    fun orderBy(orderBy: String): SelectQueryBuilder<T> {
        this.orderBy = orderBy
        return this
    }

    override fun execute() {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(uri!!, projection, selection, selectionArgs, orderBy)
            cursor?.let {
                val result = mutableListOf<T>()
                while (it.moveToNext()) {
                    map(it)?.let { item ->
                        result.add(item)
                    }
                }
                onResult(result)
            }
        } catch (e: Exception) {
            onError(e)
        } finally {
            cursor?.close()
            onCompleted()
        }
    }
}