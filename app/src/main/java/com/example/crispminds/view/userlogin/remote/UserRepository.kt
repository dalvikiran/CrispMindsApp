package com.example.crispminds.view.userlogin.remote

import android.app.Application
import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.*
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

class UserRepository(private var mUserDataSource: UserDataSource?) :
    UserDataSource {


    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null


        @JvmStatic
        fun getInstance(
            mApplication: Application,
            initRemoteRepository: Boolean
        ): UserRepository? {
            if (INSTANCE == null) {
                synchronized(UserRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = UserRepository(
                            if (initRemoteRepository) UserRemoteDataSource.getInstance(
                                mApplication
                            ) else null
                        )
                    }
                }
            }
            return INSTANCE
        }
    }



    override fun loginUser(loginDTO: LoginDTO, callback: IDataSourceCallback<AccessTokenDTO>) {
        mUserDataSource?.loginUser(loginDTO, callback)
    }

    override fun registerUser(
        registrationRequestDTO: RegistrationRequestDTO,
        callback: IDataSourceCallback<RegistrationResponceDTO>
    ) {
        mUserDataSource?.registerUser(registrationRequestDTO, callback)
    }

    override fun verifyOtp(
        emailAddress: String,
        otp: String,
        callback: IDataSourceCallback<CommonResponseDTO>
    ) {
        mUserDataSource?.verifyOtp(emailAddress, otp, callback)
    }

    override fun setPassword(
        emailAddress: String,
        password: String,
        callback: IDataSourceCallback<CommonResponseDTO>
    ) {
        mUserDataSource?.setPassword(emailAddress, password, callback)
    }


    override fun forgotPassword(loginId: String, callback: IDataSourceCallback<String>) {
        mUserDataSource?.forgotPassword(loginId, callback)
    }

}