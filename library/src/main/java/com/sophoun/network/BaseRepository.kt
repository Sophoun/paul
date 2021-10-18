package com.sophoun.network

import com.sophoun.network.BaseResponse.Info
import com.sophoun.network.BaseResponse.Status.CONNECTION_ERROR
import com.sophoun.network.BaseResponse.Status.SUCCESS
import com.sophoun.utils.DLog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import retrofit2.Response

open class BaseRepository {

    /**
     * Use this method for fetch data from network.
     *
     * It's return a custom object include response information and data.
     */
    suspend fun <T : Any> fetchDataFromApi(
        call: () -> Deferred<Response<T>>
    ): BaseResponse<T> {
            return this.readResponse(call())
    }

    suspend fun <T : Any> readResponse(call: Deferred<Response<T>>): BaseResponse<T> {
        try {
            val response = call.await()
            DLog.i("Url %s", response.raw().request.url.toString())
            val responseStatus = getResponseStatus(response.code())
            val responseMsg = response.message()
            val responseInfo = BaseResponse.Info(responseStatus, responseMsg)

            return if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()
                BaseResponse(responseInfo, responseBody)
            } else {
                BaseResponse(responseInfo, null)
            }
        } catch (exception: CancellationException) {
            DLog.e("Coroutine Job was canceled.")
            return BaseResponse(
                Info(
                    SUCCESS,
                    exception.message.toString()
                ),
                null
            )
        } catch (exception: Exception) {
            DLog.e("BaseRepository %s", exception.message.toString())
            return BaseResponse(
                Info(
                    CONNECTION_ERROR,
                    exception.message.toString()
                ),
                null
            )
        }
    }

    /**
     * @return [ResponseStatus] by providing a responseCode.
     */
    fun getResponseStatus(responseCode: Int) = when (responseCode) {
        in 200..226 -> BaseResponse.Status.SUCCESS
        in 300..308 -> BaseResponse.Status.REDIRECTION
        in 400..499 -> BaseResponse.Status.CLIENT_ERROR
        in 500..599 -> BaseResponse.Status.SERVER_ERROR
        else -> BaseResponse.Status.UNKNOWN
    }
}