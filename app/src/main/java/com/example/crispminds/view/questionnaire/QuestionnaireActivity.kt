package com.example.crispminds.view.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityQuestionnaireBinding

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityQuestionnaireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_questionnaire)

        initBinding()



    }

    // Assigning the databinding and viewmodel
    private fun initBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_questionnaire)
       /* viewModel = Utils.obtainBaseObservable(
            this,
            DashboardViewModel::class.java,
            this,
            binding.root
        )!!
        binding.dashboardViewModel = viewModel*/

    }
}
