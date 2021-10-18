package com.sophoun.query

import android.content.ContentResolver

/**
 * This class can use for delete data from android using ContentProvider
 *
 * Sample usage:
 * ```
 * DeleteQueryBuilder(context?.contentResolver!!)
 *      .uri(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI)
 *      .selection("${MediaStore.Audio.Playlists._ID} = ?")
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

class DeleteQueryBuilder(contentResolver: ContentResolver): QueryBuilder<Void, Void, Int>(contentResolver) {

    override fun execute() {
        try {
            val deletedRow = contentResolver.delete(uri!!, selection, selectionArgs)
            onResult(deletedRow)
        } catch (e: Exception) {
            onError(e)
        } finally {
            onCompleted()
        }
    }
}