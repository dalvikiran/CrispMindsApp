package com.example.crispminds.view.userlogin.remote

import android.content.Context
import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.*
import com.example.crispminds.retrofit_services.NetworkController
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.NetworkResponseCallback
import com.example.crispminds.utils.CustomGsonBuilder
import org.json.JSONObject

class UserRemoteDataSource : UserDataSource {

    companion object {
        private var instance: UserRemoteDataSource? = null
        private var networkController: NetworkController? = null
        private var mContext: Context? = null

        @JvmStatic
        fun getInstance(context: Context?): UserDataSource? {
            mContext = context
            networkController = NetworkController.getInstance(context!!)
            if (instance == null) {
                instance = UserRemoteDataSource()
            }
            return instance
        }
    }


    override fun loginUser(loginDTO: LoginDTO, callback: IDataSourceCallback<AccessTokenDTO>) {

        networkController?.loginUser(loginDTO, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {

                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val loginResponceDTO: LoginResponseDTO = gson.fromJson(
                    jsonObject.toString(),
                    LoginResponseDTO::class.java
                )

                if (loginResponceDTO.success != null) {
                    callback.onDataFound(loginResponceDTO.data)
                } else if (loginResponceDTO.error != null) {
                    callback.onError(loginResponceDTO.error.message)
                } else {
                    callback.onError(NetworkController.SERVER_ERROR)
                }

            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }

        })
    }


    override fun registerUser(
        registrationRequestDTO: RegistrationRequestDTO,
        callback: IDataSourceCallback<RegistrationResponceDTO>
    ) {
        networkController!!.registerUser(registrationRequestDTO, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val registrationResponceDTO: RegistrationResponceDTO = gson.fromJson(
                    jsonObject.toString(),
                    RegistrationResponceDTO::class.java
                )

                if (registrationResponceDTO.success != null) {
                    callback.onDataFound(registrationResponceDTO)
                } else if (registrationResponceDTO.error != null) {
                    callback.onError(registrationResponceDTO.error.message)
                } else {
                    callback.onError(NetworkController.SERVER_ERROR)
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(NetworkController.SERVER_ERROR)
            }
        })
    }

    override fun verifyOtp(
        emailAddress: String,
        otp: String,
        callback: IDataSourceCallback<CommonResponseDTO>
    ) {
        networkController!!.verifyOtp(emailAddress, otp, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val commonResponseDTO: CommonResponseDTO = gson.fromJson(
                    jsonObject.toString(),
                    CommonResponseDTO::class.java
                )

                if (commonResponseDTO.success != null) {
                    callback.onDataFound(commonResponseDTO)
                } else if (commonResponseDTO.error != null) {
                    callback.onError(commonResponseDTO.error.message)
                } else {
                    callback.onError(NetworkController.SERVER_ERROR)
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(NetworkController.SERVER_ERROR)
            }
        })
    }

    override fun setPassword(
        emailAddress: String,
        password: String,
        callback: IDataSourceCallback<CommonResponseDTO>
    ) {

        networkController!!.setPassword(emailAddress, password, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val commonResponseDTO: CommonResponseDTO = gson.fromJson(
                    jsonObject.toString(),
                    CommonResponseDTO::class.java
                )

                if (commonResponseDTO.success != null) {
                    callback.onDataFound(commonResponseDTO)
                } else if (commonResponseDTO.error != null) {
                    callback.onError(commonResponseDTO.error.message)
                } else {
                    callback.onError(NetworkController.SERVER_ERROR)
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(NetworkController.SERVER_ERROR)

            }
        })
    }


    override fun forgotPassword(loginId: String, callback: IDataSourceCallback<String>) {
        networkController?.forgotPassword(loginId, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {

//                callback.onDataFound("")

                val jsonObject = JSONObject(data)
                if(jsonObject.has("otp")){
                    val otpJson = jsonObject.getJSONObject("otp")
                    if (otpJson.has("otp")){
                        callback.onDataFound("OTP send Successfully. OTP : " +
                                "${otpJson.getString("otp")}")
                    }
                }else{
                    callback.onError(jsonObject.toString())
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }

        })
    }

}