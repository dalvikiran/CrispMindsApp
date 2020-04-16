package com.example.crispminds.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.master_controller.source.MasterRepository
import com.example.crispminds.modeldtos.LoginDTO
import com.example.crispminds.models.AccessTokenDTO
import com.example.crispminds.models.UserDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.ErrorCheckUtils
import com.example.crispminds.view.dashboard.DashboardActivity
import com.example.crispminds.view.questionnaire.QuestionnaireActivity
import com.example.crispminds.view.userlogin.ForgotPasswordActivity
import com.example.crispminds.view.userlogin.RegistrationActivity
import com.example.crispminds.view.userlogin.remote.UserRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch

class LoginViewModel : GenericBaseObservable {

    var context: Context
    var application: Application
    private var userRepository: UserRepository
    var showProgressBar = MutableLiveData<Boolean>()
    var sharedPreferences: SharedPreferences

    var closeActivity = MutableLiveData<Boolean>()

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var emailErrorMsg = MutableLiveData<String>("")
    var passErrorMsg = MutableLiveData<String>("")

    var isQuetionaireDone: Boolean = false

    lateinit var error: String

    var masterRepository: MasterRepository? = null

/*
    var isPasswordVisible = MutableLiveData<Boolean>(false)
    var passwordDrawable =
        MutableLiveData<Drawable>(Constants.getDrawable(context, R.drawable.ic_visibility_off))
*/


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
        isQuetionaireDone = sharedPreferences.getBoolean(Constants.IS_QUETIONAIRE_DONE, false)

        sharedPreferences.edit().putString(
            Constants.USER_EMAIL_ADDRESS,
            Constants.encrypt(
                ""
            )
        ).apply()

        masterRepository = MasterRepository.getInstance(context)

    }


    fun onLoginClick() {
        validate()
    }

    fun onRegisterClick() {
        Constants.changeActivity<RegistrationActivity>(context)
    }

    fun onForgotPasswordClick() {
        Constants.changeActivity<ForgotPasswordActivity>(context)

    }

/*
    fun onPasswordToggleClick() {
        if (isPasswordVisible.value!!) {
            passwordDrawable.value = Constants.getDrawable(context, R.drawable.ic_visibility_off)
            isPasswordVisible.value = false
        } else {
            passwordDrawable.value = Constants.getDrawable(context, R.drawable.ic_visibility_on)
            isPasswordVisible.value = true
        }
    }
*/


    private fun validate() {
//        validateEmail()
//        validatePassword()

        if (validateEmail() && validatePassword()) {

            val loginDTO = LoginDTO()
            loginDTO.username = email.value
            loginDTO.password = password.value
            loginDTO.role = Constants.ROLE
            loginDTO.sourceId = Constants.SOURCE
            loginUserServiceCall(loginDTO)

        }
    }

    private fun validateEmail(): Boolean {
        error = ErrorCheckUtils.checkValidEmail(email.value)
        if (error.isNotEmpty()) {
//            Constants.showToastMessage(context, error)
            emailErrorMsg.value = error
            return false
        } else {
            emailErrorMsg.value = ""
        }
        return true
    }

    private fun validatePassword(): Boolean {

        error = ErrorCheckUtils.checkValidPassword(password.value)
        if (error.isNotEmpty()) {
//            Constants.showToastMessage(context, error)
            passErrorMsg.value = error
            return false
        } else {
            passErrorMsg.value = ""
        }
        return true
    }


    private fun loginUserServiceCall(loginDTO: LoginDTO) {

        showProgressBar.value = true
        coroutineScope.launch {

            userRepository.loginUser(loginDTO, object : IDataSourceCallback<AccessTokenDTO> {

                override fun onDataFound(data: AccessTokenDTO) {

                    if(data!= null){

                        sharedPreferences.edit().putString(
                            Constants.ACCESS_TOKEN,
                            Constants.encrypt(
                                data.access_token
                            )
                        ).apply()

                        sharedPreferences.edit().putString(
                            Constants.REGISTRATION_DATA,
                            Constants.encrypt(
                                Constants.saveUserDataInSharedPreference(
                                    data.user
                                )
                            )
                        ).apply()


                    }

                    if (isQuetionaireDone) {
                        Constants.changeActivity<DashboardActivity>(context)
                    } else {
                        Constants.changeActivity<QuestionnaireActivity>(context)
                    }
                    //                Constants.changeActivity<DashboardActivity>(context)
                    showProgressBar.value = false
                    closeActivity.value = true
                }

                override fun onError(error: String?) {
                    showProgressBar.value = false
                    getmSnackbar().value = error
                }

            })


/* Older response

            val response =
                UserApi.retrofitService.loginUser(email.value.toString(), password.value.toString())
            try {
                var userDetail = response.await()
                val userDto: UserDTO = userDetail.data


                sharedPreferences.edit()
                    .putString(Constants.USER_DTO, Constants.encrypt(Constants.setUserDTO(userDto)))
                    .apply()

//                Constants.showToastMessage(context,"Login Success")
                if (isQuetionaireDone) {
                    Constants.changeActivity<DashboardActivity>(context)
                } else {
                    Constants.changeActivity<QuestionnaireActivity>(context)
                }
//                Constants.changeActivity<DashboardActivity>(context)
                showProgressBar.value = false

            } catch (e: Exception) {
//                Constants.showToastMessage(context,"Error ${e.message}")
//                Constants.showToastMessage(context, Constants.ERROR_MSG)
                passErrorMsg.value = Constants.ERROR_MSG
                showProgressBar.value = false

            }
*/

        }

    }

}