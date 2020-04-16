package com.example.crispminds.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.ErrorCheckUtils
import com.example.crispminds.view.userlogin.RegistrationActivity
import com.example.crispminds.view.userlogin.VerifyOTPActivity
import com.example.crispminds.view.userlogin.remote.UserRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : GenericBaseObservable {

    var context: Context
    var application: Application
    var userRepository: UserRepository

    var emailId = MutableLiveData<String>("")
    var emailErrorMsg = MutableLiveData<String>("")

    var showProgressBar = MutableLiveData<Boolean>()
    var sharedPreferences: SharedPreferences

    lateinit var error: String

    constructor(
        application: Application,
        owner: LifecycleOwner?,
        view: View?,
        userRepository: UserRepository
    ) : super(
        application,
        owner,
        view
    ) {
        this.context = application.applicationContext
        this.application = application
        this.userRepository = userRepository
        sharedPreferences = Constants.getSharedPreferences(context)

    }


    fun onRegisterClick() {
        Constants.changeActivity<RegistrationActivity>(context)
    }


    fun onSendOtpClick() {
        if (validateEmail()) {
            verifyEmailAndSendOtpServiceCall()
        }
    }


    private fun validateEmail(): Boolean {
        error = ErrorCheckUtils.checkValidEmail(emailId.value)
        if (error.isNotEmpty()) {
//            Constants.showToastMessage(context, error)
            emailErrorMsg.value = error
            return false
        } else {
            emailErrorMsg.value = ""
        }
        return true
    }

    private fun verifyEmailAndSendOtpServiceCall() {

        showProgressBar.value = true
        coroutineScope.launch {

            emailId.value?.let {
                userRepository.forgotPassword(it, object : IDataSourceCallback<String> {

                    override fun onDataFound(data: String) {
                        Constants.showToastMessage(context, "${data}")

                        sharedPreferences.edit().putString(
                            Constants.USER_EMAIL_ADDRESS,
                            Constants.encrypt(
                                emailId.value
                            )
                        ).apply()

                        showProgressBar.value = false
                        Constants.changeActivity<VerifyOTPActivity>(context)
                    }

                    override fun onError(error: String?) {
                        showProgressBar.value = false
                        getmSnackbar().value = error
                    }

                })
            }

        }


    }

}