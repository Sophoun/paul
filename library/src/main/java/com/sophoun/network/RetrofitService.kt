package com.sophoun.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit.SECONDS

class RetrofitService private constructor(
    private val baseUrl: String,
    private val converterFactory: Converter.Factory,
    private val callAdapterFactories: List<CallAdapter.Factory>,
    private val headers: List<Pair<String, String>>,
    private val interceptors: List<Interceptor>,
    private val connectTimeOut: Long,
    private val readTimeOut: Long
) {

    /**
     * OkHttpClient
     */
    private val okHttpClient by lazy {
        OkHttpClient.Builder().also { builder ->
            interceptors.forEach {
                builder.addInterceptor(it)
            }
            builder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                request.method(chain.request().method, chain.request().body)
                headers.forEach { pair ->
                    request.addHeader(pair.first, pair.second)
                }
                return@addInterceptor chain.proceed(request.build())
            }
            builder.connectTimeout(connectTimeOut, SECONDS)
            builder.readTimeout(readTimeOut, SECONDS)
        }.build()
    }

    /**
     * Get retrofit service
     */
    fun <T> service(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            callAdapterFactories.forEach { retrofit.addCallAdapterFactory(it) }
        return retrofit.build().create<T>(service)
    }

    /**
     * Use this Builder class for building retrofit service
     */
    class Builder {
        private lateinit var url: String
        private lateinit var converterFactory: Converter.Factory
        private val callAdapterFactories = arrayListOf<CallAdapter.Factory>()
        private val headers = ArrayList<Pair<String, String>>()
        private val interceptors = ArrayList<Interceptor>()
        private var connectTimeOut: Long = 30
        private var readTimeOut: Long = 30

        fun baseUrl(url: String): Builder {
            this.url = url
            return this
        }

        fun converterFactory(converterFactory: Converter.Factory): Builder {
            this.converterFactory = converterFactory
            return this
        }

        fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): Builder {
            this.callAdapterFactories.add(callAdapterFactory)
            return this
        }

        fun addHeader(key: String, value: String): Builder {
            this.headers.add(Pair(key, value))
            return this
        }

        fun addHeaders(headers: List<Pair<String, String>>): Builder {
            this.headers.addAll(headers)
            return this
        }

        fun addInterceptors(interceptors: List<Interceptor>): Builder {
            this.interceptors.addAll(interceptors)
            return this
        }

        fun setConnectTimeOut(second: Long): Builder {
            this.connectTimeOut = second
            return this
        }

        fun setReedTimeOut(second: Long): Builder {
            this.readTimeOut = second
            return this
        }

        fun build(): RetrofitService = RetrofitService(url, converterFactory, callAdapterFactories, headers, interceptors, connectTimeOut, readTimeOut)
    }
}