package com.example.crispminds.view.userlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivitySetPasswordBinding
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.utils.view_model_factory.ViewModelFactory
import com.example.crispminds.view.dashboard.viewmodel.DashboardViewModel
import com.example.crispminds.viewmodels.RegistrationViewModel

class SetPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivitySetPasswordBinding
    lateinit var viewModel: RegistrationViewModel
    lateinit var viewModelFactory: ViewModelFactory
    var progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        observar()

    }

    fun bindViewModel() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_password)

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


    }

    fun observar() {

        viewModel.newPassword.observe(this, Observer {
            viewModel.newPasswordErrorMsg.value = ""
        })
        viewModel.confirmPassword.observe(this, Observer {
            viewModel.confirmPasswordErrorMsg.value = ""
        })


        viewModel.closeActivity.observe(this, Observer {
            finish()
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
