package com.example.crispminds.retrofit_services

import android.content.Context
import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.RegistrationRequestDTO
import com.example.crispminds.retrofit_services.network_utils.HTTP_RETROFIT_FAILURE
import com.example.crispminds.retrofit_services.network_utils.HTTP_SUCCESS
import com.example.crispminds.retrofit_services.network_utils.NetworkResponseCallback
import com.example.crispminds.retrofit_services.network_utils.getStringResponseFromRaw
import com.example.crispminds.utils.Constants
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkController {

    companion object {
        var instance: NetworkController? = null
        lateinit var mContext: Context

        const val SERVER_ERROR = "Something went wrong on the server"

        fun getInstance(context: Context): NetworkController {
            mContext = context

            if (instance == null) {
                instance = NetworkController()
            }

            return instance as NetworkController
        }

        const val contentType = "application/json"
        const val headers = "1"
    }

    private class RetrofitServiceTask(var networkResponseCallback: NetworkResponseCallback) :
        Callback<ResponseBody> {

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.code() == HTTP_SUCCESS) {
//                val jsonObject = JSONObject(getStringResponseFromRaw(response))
                getStringResponseFromRaw(response)?.let { networkResponseCallback.onSuccess(it) }
            } else {
                val jsonError = getStringResponseFromRaw(response.errorBody()!!)
                try {
                    val jsonObject = JSONObject(jsonError.toString())
                    networkResponseCallback.onSuccess(jsonObject.toString())
                } catch (e: Exception) {
                    networkResponseCallback.onError(response.code(), SERVER_ERROR)
                }
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            networkResponseCallback.onError(HTTP_RETROFIT_FAILURE, t.message.toString())
        }

    }


    fun loginUser(loginDTO: LoginDTO, callback: NetworkResponseCallback) {
        val response = UserApi.retrofitService.loginUser( loginDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }


    fun getCategoryDetails(callback: NetworkResponseCallback) {
        val response = DashboardApi.retrofitService.fetchCategoryDetails()
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun fetchEmotionMasters(callback: NetworkResponseCallback) {
        val response = DashboardApi.retrofitService.fetchEmotionMasters(
            headers,
            contentType,
            0,
            0,
            0,
            "",
            "",
            "asc",
            "emotionId",
            "",
            true
        )
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }




    fun registerUser(
        registrationRequestDTO: RegistrationRequestDTO,
        callback: NetworkResponseCallback
    ) {

        val response = UserApi.retrofitService.registraterUser(registrationRequestDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun verifyOtp(emailAddress: String, otp: String, callback: NetworkResponseCallback) {

        val response = UserApi.retrofitService.verifyOtp(emailAddress, otp, Constants.SOURCE)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun setPassword(emailAddress: String, password: String, callback: NetworkResponseCallback) {

        val response = UserApi.retrofitService.setPassword(emailAddress, password)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }


    fun forgotPassword(loginId: String, callback: NetworkResponseCallback) {

        val response = UserApi.retrofitService.forgotPassword(loginId)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    /* Video Sections */

    fun fetchVideosListByCategoryId(
        categoryId: Long,
        start: Int,
        length: Int,
        search: String,
        callback: NetworkResponseCallback
    ) {
        val service = DashboardApi.retrofitService.fetchVideosListByCategoryId(
            headers,
            contentType,
            1L,
            0,
            start,
            length,
            "",
            search,
            "desc",
            "videoTitle",
            "",
            true
        )
        val authenticationResponseCall: Call<ResponseBody> = service
        authenticationResponseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun fetchVideoDetails(videoId: Long, callback: NetworkResponseCallback) {

        val service = DashboardApi.retrofitService.fetchVideosDetailsByVideoId(headers, videoId)
        val authenticationResponseCall: Call<ResponseBody> = service
        authenticationResponseCall.enqueue(RetrofitServiceTask(callback))
    }


}