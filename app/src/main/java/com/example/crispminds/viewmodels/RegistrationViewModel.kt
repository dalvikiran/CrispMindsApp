package com.example.crispminds.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.CommonResponseDTO
import com.example.crispminds.models.RegistrationRequestDTO
import com.example.crispminds.models.RegistrationResponceDTO
import com.example.crispminds.models.SendOtpToEmail
import com.example.crispminds.retrofit_services.UserApi
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.ErrorCheckUtils
import com.example.crispminds.view.userlogin.LoginActivity
import com.example.crispminds.view.userlogin.SetPasswordActivity
import com.example.crispminds.view.userlogin.VerifyOTPActivity
import com.example.crispminds.view.userlogin.remote.UserRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch


class RegistrationViewModel : GenericBaseObservable {

    var application: Application
    var userRepository: UserRepository

    private var registrationRequestDTO: RegistrationRequestDTO? = null
    private lateinit var otpString: String
    var context: Context

    var userName = MutableLiveData<String>()
    var firstName = MutableLiveData<String>("")
    var lastName = MutableLiveData<String>("")
    var emailId = MutableLiveData<String>("")
    var mobileNumber = MutableLiveData<String>("")
    var closeActivity = MutableLiveData<Boolean>()
    var userOtp = MutableLiveData<String>("")
    var newPassword = MutableLiveData<String>("")
    var confirmPassword = MutableLiveData<String>("")
    var sharedPreferences: SharedPreferences
    var showProgressBar = MutableLiveData<Boolean>()

    // Error Fields
    var firstNameErrorMsg = MutableLiveData<String>("")
    var lastNameErrorMsg = MutableLiveData<String>("")
    var emailErrorMsg = MutableLiveData<String>("")
    var mobileNoErrorMsg = MutableLiveData<String>("")
    var newPasswordErrorMsg = MutableLiveData<String>("")
    var confirmPasswordErrorMsg = MutableLiveData<String>("")


    var otpErrorMsg = MutableLiveData<String>("")

    lateinit var error: String // common validation error message variable
/*

    init {
        this.context = context
        sharedPreferences = Constants.getSharedPreferences(context)
        val regData =
            Constants.decrypt(sharedPreferences.getString(Constants.REGISTRATION_DATA, ""))

        if (regData != null && regData != "") {
            registrationRequestDTO = Constants.getRegistrationDataFromSharedPreference(regData)
        }

    }
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
//        val regData = Constants.decrypt(sharedPreferences.getString(Constants.REGISTRATION_DATA, ""))
        emailId.value = Constants.decrypt(sharedPreferences.getString(Constants.USER_EMAIL_ADDRESS, ""))

        /*if (regData != null && !regData.equals("")) {
            registrationRequestDTO = Constants.getRegistrationDataFromSharedPreference(regData)
        }*/
    }

    fun onLoginClick() {
//        Constants.changeActivity<RegistrationActivity>(context)
        closeActivity.value = true
    }

    fun onRegisterClick() {
        validate()
    }

    private fun validate() {
//        validateFirstName()
//        validateLastName()
//        validateEmailId()
//        validateMobileNumber()
        if (validateFirstName() && validateLastName() && validateEmailId() && validateMobileNumber()) {

            val registrationDTO = RegistrationRequestDTO(
                0L,firstName.value,lastName.value,
                emailId.value.toString(), emailId.value.toString(),
                mobileNumber.value.toString(),null, null
            )
            register(registrationDTO)
/*
            sharedPreferences.edit().putString(
                Constants.REGISTRATION_DATA,
                Constants.encrypt(Constants.saveRegistrationData(registrationDTO))
            ).apply()

            Constants.changeActivity<VerifyOTPActivity>(context)
*/
        }
    }

    private fun validateFirstName(): Boolean {
        error = ErrorCheckUtils.checkValidFirstName(firstName.value)
        if (!error.isEmpty()) {
            firstNameErrorMsg.value = error
            return false
        } else {
            firstNameErrorMsg.value = ""
        }
        return true
    }

    private fun validateLastName(): Boolean {
        error = ErrorCheckUtils.checkValidLastName(lastName.value)
        if (!error.isEmpty()) {
            lastNameErrorMsg.value = error
            return false
        } else {
            lastNameErrorMsg.value = ""
        }
        return true
    }

    private fun validateEmailId(): Boolean {
        error = ErrorCheckUtils.checkValidEmail(emailId.value)
        if (!error.isEmpty()) {
            emailErrorMsg.value = error
            return false
        } else {
            emailErrorMsg.value = ""
        }
        return true
    }

    private fun validateMobileNumber(): Boolean {
        error = ErrorCheckUtils.checkValidMobile(mobileNumber.value)
        if (!error.isEmpty()) {
            mobileNoErrorMsg.value = error
            return false
        } else {
            mobileNoErrorMsg.value = ""
        }
        return true
    }

    private fun register(registrationRequestDTO: RegistrationRequestDTO) {

        showProgressBar.value = true
        coroutineScope.launch {

            userRepository.registerUser(registrationRequestDTO, object : IDataSourceCallback<RegistrationResponceDTO> {
                override fun onDataFound(data: RegistrationResponceDTO) {
                    showProgressBar.value = false
                    if (data.success != null) {
                        Log.e("OTP : " , "${data.otp}")
                        Constants.showToastMessage(context, "${data.success.message} OTP:${data.otp} ")

                        sharedPreferences.edit().putString(
                            Constants.USER_EMAIL_ADDRESS,
                            Constants.encrypt(
                                registrationRequestDTO.emailAddress
                            )
                        ).apply()

                        Constants.changeActivity<VerifyOTPActivity>(context)
                        closeActivity.value = true
                    }
                }

                override fun onError(error: String?) {
                    showProgressBar.value = false
                    Constants.showToastMessage(context, "${error}")
                }
            })
            /*try {
                var response = UserApi.retrofitService.checkEmailExitsStatus(registrationRequestDTO)
                var result = response.await()
                Log.e("Register result", result.toString())

                showProgressBar.value = false

                if (!result.Status!!) {

                    sharedPreferences.edit().putString(
                        Constants.REGISTRATION_DATA,
                        Constants.encrypt(
                            Constants.saveRegistrationDataInSharedPreference(
                                registrationRequestDTO
                            )
                        )
                    ).apply()

                    Constants.changeActivity<VerifyOTPActivity>(context)
                } else {
                    Constants.showToastMessage(context, "User already exist")

                    registrationRequestDTO.use = result.UserId
                    sharedPreferences.edit().putString(
                        Constants.REGISTRATION_DATA,
                        Constants.encrypt(
                            Constants.saveRegistrationDataInSharedPreference(
                                registrationRequestDTO
                            )
                        )
                    ).apply()
                }
*//*
                if (result.code == RESULT_OK) {
                    Constants.changeActivity<DashboardActivity>(context)
                    closeActivity.value = true
                }
*//*


            } catch (e: Exception) {
                Constants.showToastMessage(context, "Error ${e.message}")
                showProgressBar.value = false

            }*/


        }

    }

    fun sendOtpToEmail() {
        showProgressBar.value = true

        try {

            /* var registrationRequestDTO = Constants.getRegistrationDataFromSharedPreference(
                 Constants.decrypt(
                     sharedPreferences.getString(
                         Constants.REGISTRATION_DATA,
                         ""
                     )?.toString()
                 )
             )!!*/

            Log.e("Register data ", registrationRequestDTO.toString())


            if (!Constants.generateOtp().toString().equals("")) {

                otpString = Constants.generateOtp().toString()
                registrationRequestDTO!!.otp = otpString


                coroutineScope.launch {
                    try {
                        var response =
                            UserApi.retrofitService.sendOtpToEmail(
                                SendOtpToEmail(
                                    registrationRequestDTO!!.emailAddress.toString(),
                                    registrationRequestDTO!!.otp!!
                                )
                            )
                        var result = response.await()
                        Log.e("Register result", result.toString())

                        if (result) {
                            Toast.makeText(
                                context,
                                "OTP : " + otpString,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Constants.showToastMessage(context, result.toString())
                        }

                        showProgressBar.value = false
                    } catch (e: Exception) {
                        Constants.showToastMessage(context, "Error ${e.message}")
                        showProgressBar.value = false

                    }
                }
            } else {
                showProgressBar.value = false
            }

        } catch (e: Exception) {
            showProgressBar.value = false
            Log.e("Exception : ", "Error ${e.message}")
        }


    }

    fun onVerifyOtpClick() {

        if (validateOtp()) {
            verifyOtp()
        }
    }


    private fun validateOtp(): Boolean {
        error = ErrorCheckUtils.checkValidOtp(userOtp.value)
        if (error.isNotEmpty()) {
            otpErrorMsg.value = error
            return false
        } else {
            otpErrorMsg.value = ""
        }
        return true
    }

    private fun verifyOtp(){
        showProgressBar.value = true
        userRepository.verifyOtp(emailId.value!!, userOtp.value!!, object : IDataSourceCallback<CommonResponseDTO>{
            override fun onDataFound(data: CommonResponseDTO) {
                Constants.showToastMessage(context, "OTP verified Successfully")
                showProgressBar.value = false
                Constants.changeActivity<SetPasswordActivity>(context)
                closeActivity.value = true
            }

            override fun onError(error: String?) {
                Constants.showToastMessage(context, error!!)
                showProgressBar.value = false
            }
        })
    }

    fun backToLogin() {
        closeActivity.value = true
    }

    fun gotoTC() {


    }

    fun onSavePasswordClick() {
        val pass = newPassword.value!!

        if (pass.isNotEmpty()) {

            if ((pass == confirmPassword.value)) {

                /* if (Constants.passwordRegex.matcher(confirmPasswordMutableLiveData.value).find()) {
                     savePasswordAndRegister(confirmPasswordMutableLiveData.value!!)
                 }else{
                     Constants.showToastMessage(context, "Please enter correct password")
                 }*/
                savePasswordAndRegister(confirmPassword.value!!)

            } else {
                confirmPasswordErrorMsg.value = "Password did not match"
            }
        } else {
            newPasswordErrorMsg.value = "Please enter password"
        }
    }

    private fun savePasswordAndRegister(password: String) {

        showProgressBar.value = true

/*
        var registrationRequestDTO = Constants.getRegistrationDataFromSharedPreference(
            Constants.decrypt(
                sharedPreferences.getString(
                    Constants.REGISTRATION_DATA,
                    ""
                )?.toString()
            )
        )!!
*/

//        registrationRequestDTO!!.password = password
        coroutineScope.launch {
            try {
                userRepository.setPassword(emailId.value!!, password!!, object : IDataSourceCallback<CommonResponseDTO>{
                    override fun onDataFound(data: CommonResponseDTO) {
                        Constants.showToastMessage(context, data.success!!.message)
                        showProgressBar.value = false

                        Constants.changeActivity<LoginActivity>(context)
                        closeActivity.value = true
                    }

                    override fun onError(error: String?) {
                        showProgressBar.value = false
                        Constants.showToastMessage(context, error!!)
                    }
                })
                /*var response = UserApi.retrofitService.registration(registrationRequestDTO!!)
                var result = response.await()
                Log.e("Register result", result.toString())

                showProgressBar.value = false
                Constants.changeActivity<LoginActivity>(context)

                showProgressBar.value = false*/
            } catch (e: Exception) {
                Constants.showToastMessage(context, "Error ${e.message}")
                showProgressBar.value = false

            }
        }

    }

}