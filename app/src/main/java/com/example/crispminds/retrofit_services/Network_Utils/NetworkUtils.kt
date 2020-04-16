package com.example.crispminds.retrofit_services.network_utils

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

const val TIMEOUT = 60 * 1000

const val HTTP_SUCCESS = 200
const val HTTP_RETROFIT_FAILURE = 0


private var viewModelJob = Job()

val coroutineScope = CoroutineScope(
    viewModelJob + Dispatchers.Main
)

val client: OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(BasicAuthInterceptor("admin", "admin"))
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(SERVER_URL)
//    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()


fun getStringResponseFromRaw(response: ResponseBody): String? {
    var reader: BufferedReader?
    val sb = java.lang.StringBuilder()
    try {
        reader = BufferedReader(InputStreamReader(response.byteStream()))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return sb.toString()
}


fun getStringResponseFromRaw(response: Response<ResponseBody>): String? {
    var reader: BufferedReader? = null
    val sb = java.lang.StringBuilder()
    try {
        reader = BufferedReader(InputStreamReader(response.body()!!.byteStream()))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return sb.toString()
}

