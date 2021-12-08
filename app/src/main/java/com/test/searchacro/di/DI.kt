package com.test.searchacro.di

import com.test.searchacro.network.CacheHeaderInterceptor
import com.test.searchacro.network.NetworkInterceptor
import com.test.searchacro.SearchApplication
import com.test.searchacro.network.ApiClient
import com.test.searchacro.repository.AcronymRepo
import com.test.searchacro.repository.AcronymRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

interface Dispatcher {
    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
}

object ScDispatcher: Dispatcher {
    override val MAIN: CoroutineDispatcher by lazy { Dispatchers.Main }
    override val IO: CoroutineDispatcher by lazy { Dispatchers.IO }
}

object DI {

    val baseUrl = "http://www.nactem.ac.uk"

    val acronymRepo : AcronymRepo by lazy {
        AcronymRepository(apiClient)

    }

    val apiClient: ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }

    val dispatcher = ScDispatcher

    val retrofit by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    val okHttpClient by lazy {
        val cacheFile = File(SearchApplication.application.cacheDir, "okhttp_cache")
        val cacheSize = 10L * 1024L * 1024L
        val cache = Cache(cacheFile, cacheSize)
        val cacheHeaderInterceptor = CacheHeaderInterceptor()
        OkHttpClient.Builder()
                .addInterceptor(NetworkInterceptor())
                .addNetworkInterceptor(cacheHeaderInterceptor)
                .addInterceptor(cacheHeaderInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .build()
    }
}


