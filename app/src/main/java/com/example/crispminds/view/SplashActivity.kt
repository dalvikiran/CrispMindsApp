package com.example.crispminds.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivitySplashBinding
import com.example.crispminds.master_controller.sync.MasterSyncIntentService
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.Constants.Companion.SPLASH_TIME_OUT
import com.example.crispminds.view.userlogin.LoginActivity
import com.example.kotlin_example.walk_through.WalkThroughActivity

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    var isFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startImmediateSync(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        isFirstTime = Constants.getIsFirstTime(this@SplashActivity)!!

        Handler().postDelayed(
            {
                if (isFirstTime) {
                    Constants.changeActivity<WalkThroughActivity>(this@SplashActivity)
//                    Constants.changeActivity<DashboardActivity>(this@SplashActivity)
                } else {
                    Constants.changeActivity<LoginActivity>(this@SplashActivity)
//                    Constants.changeActivity<DashboardActivity>(this@SplashActivity)
                }
                finish()
            }, SPLASH_TIME_OUT
        )

    }


    fun startImmediateSync(context: Context) {
        val intentToSyncImmediately = Intent(context, MasterSyncIntentService::class.java)
        context.startService(intentToSyncImmediately)
    }

}
