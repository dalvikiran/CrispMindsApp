package com.example.crispminds.view.userlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityLoginBinding
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.viewmodels.LoginViewModel


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    var progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        viewModelObserver()
    }

    private fun bindViewModel() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = Utils.obtainBaseObservable(
            this,
            LoginViewModel::class.java,
            this,
            binding.root
        )!!
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        spannableText()


    }

    private fun viewModelObserver() {

        viewModel.email.observe(this, Observer {
            viewModel.emailErrorMsg.value = ""
        })
        viewModel.password.observe(this, Observer {
            viewModel.passErrorMsg.value = ""
        })

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(this, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

        viewModel.closeActivity.observe(this, Observer {
            finish()
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
