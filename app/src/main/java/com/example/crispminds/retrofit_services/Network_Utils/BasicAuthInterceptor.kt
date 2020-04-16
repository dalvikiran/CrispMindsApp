package com.example.crispminds.retrofit_services.network_utils

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(var userName : String, var password : String) : Interceptor {


    lateinit var credential : String
    init {
        this.credential = Credentials.basic(userName,password)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", credential)
            .build()

        return chain.proceed(authenticatedRequest)
    }

}