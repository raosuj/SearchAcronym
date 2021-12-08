package com.test.searchacro.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.test.searchacro.SearchApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected(SearchApplication.application)) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }
}

class NoNetworkException internal constructor() : IOException("Please check Network Connection")

fun isConnected(application: Application): Boolean {
    val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}