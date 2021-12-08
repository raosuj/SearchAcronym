package com.test.searchacro.network

import okhttp3.Interceptor;
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheHeaderInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                if (!request.method().contentEquals("GET")) {
                        return chain.proceed(request)
                }
                val newRequest = request.newBuilder()
                        .header("Cache-Control", "max-age=${TimeUnit.DAYS.toSeconds(1)}")
                        .build()
                val originalResponse = chain.proceed(newRequest)
                val maxAge = TimeUnit.DAYS.toSeconds(1)
                return originalResponse.newBuilder()
                        .header("Cache-Control", String.format("max-age=%d", maxAge))
                        .build()
        }
}