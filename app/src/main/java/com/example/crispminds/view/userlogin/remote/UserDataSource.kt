package com.example.crispminds.view.userlogin.remote

import androidx.annotation.NonNull
import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.*
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

interface UserDataSource {

    fun loginUser(
        @NonNull loginDTO: LoginDTO,
        @NonNull callback: IDataSourceCallback<AccessTokenDTO>
    )

    fun registerUser(
        @NonNull registrationRequestDTO: RegistrationRequestDTO,
        @NonNull callback: IDataSourceCallback<RegistrationResponceDTO>
    )

    fun verifyOtp(
        @NonNull emailAddress: String, @NonNull otp: String,
        @NonNull callback: IDataSourceCallback<CommonResponseDTO>
    )

    fun setPassword(
        @NonNull emailAddress: String, @NonNull password: String,
        @NonNull callback: IDataSourceCallback<CommonResponseDTO>
    )

    fun forgotPassword(@NonNull loginId: String, @NonNull callback: IDataSourceCallback<String>)


}