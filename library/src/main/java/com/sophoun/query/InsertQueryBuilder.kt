package com.sophoun.query

import android.content.ContentResolver
import android.content.ContentValues

/**
 * This class can use for insert a list of data to
 * android database using ContentProvider
 *
 * Sample usage:
 *
 * ```
 * InsertQueryBuilder<Album>(context?.contentResolver!!)
 *      .uri(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI)
 *      .mapper {
 *      arrayOf("p1", "p2", "p3", "p4", "p5")
 *          .map {
 *              ContentValues().apply { put(MediaStore.Audio.Playlists.NAME, it) }
 *          }
 *      }.onCompleted {
 *          DLog.i("Result", "Completed")
 *      }
 *      .onResult {
 *          DLog.i("Result", it.toString())
 *      }.onError {
 *          DLog.e("Result", it.toString())
 *      }.execute()
 * ```
 */

class InsertQueryBuilder(contentResolver: ContentResolver): QueryBuilder<Any?, List<ContentValues>, Int>(contentResolver) {

    override fun execute() {
        try {
            val insertedRow = contentResolver.bulkInsert(uri!!, map(null)?.toTypedArray()!!)
            onResult(insertedRow)
        } catch (e: Exception) {
            onError(e)
        } finally {
            onCompleted()
        }
    }
}