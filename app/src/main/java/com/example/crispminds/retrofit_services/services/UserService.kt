package com.example.crispminds.retrofit_services.services

import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.EmailExitsStatus
import com.example.crispminds.models.RegistrationRequestDTO
import com.example.crispminds.models.RegistrationResponseDTO
import com.example.crispminds.models.SendOtpToEmail
import com.example.crispminds.retrofit_services.network_utils.*
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST(LOGIN_URL)
    fun loginUser(
        @Body userCredentials: LoginDTO
    ): Call<ResponseBody>

    // New Services
    @POST(REGISTER_URL)
    fun registraterUser(@Body registrationRequestDTO: RegistrationRequestDTO): Call<ResponseBody>

    @POST(VERIFY_OTP_URL)
    fun verifyOtp(
        @Query("loginId") emailAddress: String,
        @Query("otp") otp: String,
        @Query("sourceId") sourceId: String
    ): Call<ResponseBody>

    @POST(SET_PASSWORD_URL)
    fun setPassword(
        @Query("email") emailAddress: String,
        @Query("newPassword") password: String
    ): Call<ResponseBody>


    @POST(FORGOT_PASSWORD_URL)
    fun forgotPassword(@Query("loginId") loginId: String): Call<ResponseBody>



    /* ----------------------------------- Old Apis Services -------------------------------------------- */
    @POST(CHECK_EMAIL_EXIST_URL)
    fun checkEmailExitsStatus(@Body registrationRequestDTO: RegistrationRequestDTO): Deferred<EmailExitsStatus>

    @POST(GENERATE_OTP_URL)
    fun sendOtpToEmail(@Body sendOtpToEmail: SendOtpToEmail): Deferred<Boolean>

    @POST(REGISTER_URL)
    fun registration(@Body registrationRequestDTO: RegistrationRequestDTO): Deferred<RegistrationResponseDTO>


}
