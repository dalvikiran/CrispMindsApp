package com.example.crispminds.view.userlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityRegistrationBinding
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.utils.view_model_factory.ViewModelFactory
import com.example.crispminds.viewmodels.RegistrationViewModel

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: RegistrationViewModel
    lateinit var viewModelFactory: ViewModelFactory
    var progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        observar()
    }

    fun bindViewModel() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

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
//        throw IOException("Required")

    }

    private fun observar() {


        viewModel.firstName.observe(this, Observer {
            viewModel.firstNameErrorMsg.value = ""
        })
        viewModel.lastName.observe(this, Observer {
            viewModel.lastNameErrorMsg.value = ""
        })
        viewModel.emailId.observe(this, Observer {
            viewModel.emailErrorMsg.value = ""
        })
        viewModel.mobileNumber.observe(this, Observer {
            viewModel.mobileNoErrorMsg.value = ""
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
