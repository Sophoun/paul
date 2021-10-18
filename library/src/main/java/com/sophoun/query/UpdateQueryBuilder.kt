package com.sophoun.query

import android.content.ContentResolver
import android.content.ContentValues

/**
 * This class can use for update data on
 * android database using ContentProvider
 *
 * Sample usage:
 * ```
 * UpdateQueryBuilder<Album>(context?.contentResolver!!)
 *      .uri(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI)
 *      .mapper {
 *          ContentValues().apply {
 *              put(MediaStore.Audio.Playlists.NAME, "NEW NAME")
 *          }
 *      }.selection("${MediaStore.Audio.Playlists._ID} = ?")
 *      .selectionArgs(id.toString())
 *      .onResult {
 *          DLog.i("Result", it.toString())
 *      }.onError {
 *          DLog.e("Result", it.message!!)
 *      }.onCompleted {
 *          DLog.i("Result", "Completed")
 *      }.execute()
 * ```
 */

class UpdateQueryBuilder(contentResolver: ContentResolver): QueryBuilder<Any?, ContentValues, Int>(contentResolver) {

    override fun execute() {
        try {
            val updatedRow = contentResolver.update(uri!!, map(null), selection, selectionArgs)
            onResult(updatedRow)
        } catch (e: Exception) {
            onError(e)
        } finally {
            onCompleted()
        }
    }
}