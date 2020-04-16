package com.example.crispminds.view.userlogin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityVerifyOtpBinding
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.utils.view_model_factory.ViewModelFactory
import com.example.crispminds.viewmodels.RegistrationViewModel

class VerifyOTPActivity : AppCompatActivity() {

    lateinit var binding: ActivityVerifyOtpBinding
    lateinit var viewModel: RegistrationViewModel
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var sharedPreferences: SharedPreferences
    var progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        observer()
    }

    fun bindViewModel() {

        // initialising the ui and view model

        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp)

        viewModelFactory = ViewModelFactory(this)
        viewModel = Utils.obtainBaseObservable(
            this,
            RegistrationViewModel::class.java,
            this,
            binding.root
        )!!
//            ViewModelProviders.of(this, viewModelFactory).get(RegistrationViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        spannableText()
        // send Otp on respective email
//        viewModel.sendOtpToEmail()

    }

    private fun observer() {

        // Displaying progress bar
        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(this, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

        // On Click of login
        viewModel.closeActivity.observe(this, Observer {
            finish()
        })

        viewModel.userOtp.observe(this, Observer {
            viewModel.otpErrorMsg.value = ""
        })

    }


    fun spannableText() {
        Utils.getSpannableText(
            this,
            binding.content.alreadyHaveAccTxt,
            R.string.already_a_member,
            R.color.colorPrimary,
            18,
            23
        )

        Utils.twoWordsSpannableText(
            this,
            binding.content.termsAndPrivacyTxt,
            R.string.terms_and_privacy_text,
            R.color.colorPrimary,
            40,
            57,
            R.color.colorPrimary,
            62,
            77
        )


    }

}
