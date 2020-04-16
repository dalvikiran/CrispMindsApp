package com.example.crispminds.retrofit_services

import com.example.crispminds.retrofit_services.network_utils.retrofit
import com.example.crispminds.retrofit_services.services.DashboardService
import com.example.crispminds.retrofit_services.services.UserService


object UserApi {
    val retrofitService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}


object DashboardApi {
    val retrofitService: DashboardService by lazy {
        retrofit.create(DashboardService::class.java)
    }
}