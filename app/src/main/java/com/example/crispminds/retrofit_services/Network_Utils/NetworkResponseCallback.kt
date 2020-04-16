package com.example.crispminds.retrofit_services.network_utils

interface NetworkResponseCallback {

    fun onSuccess(data : String )

    fun onError(errorCode : Int, errorData : String)

}