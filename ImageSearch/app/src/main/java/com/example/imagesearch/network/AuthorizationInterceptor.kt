package com.example.imagesearch.network

import com.example.imagesearch.Constants.Companion.AUTH_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK %s".format(AUTH_KEY) // REST API 키
            ).build()

        return chain.proceed(newRequest)
    }
}