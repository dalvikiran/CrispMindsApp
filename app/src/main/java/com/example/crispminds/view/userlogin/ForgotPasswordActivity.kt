package com.example.crispminds.view.userlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityForgotPasswordBinding
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.utils.view_model_factory.ViewModelFactory
import com.example.crispminds.viewmodels.ForgotPasswordViewModel

class ForgotPasswordActivity : AppCompatActivity() {


    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var viewModel: ForgotPasswordViewModel
    var progressBar = CustomProgressBar()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        viewModelObserver()
    }


    private fun bindViewModel() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        viewModel = Utils.obtainBaseObservable(
            this,
            ForgotPasswordViewModel::class.java,
            this,
            binding.root
        )!!
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        throw IOException("Required")
        spannableText()


    }

    private fun viewModelObserver() {

        viewModel.emailId.observe(this, Observer {
            viewModel.emailErrorMsg.value = ""
        })

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(this, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })
    }

    private fun spannableText() {

        Utils.getSpannableText(
            this,
            binding.content.dontHaveAccTxt,
            R.string.dont_have_account,
            R.color.colorPrimary,
            23,
            31
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
