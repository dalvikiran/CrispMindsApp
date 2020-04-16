package com.example.kotlin_example.walk_through

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityWalkThroughBinding
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.userlogin.LoginActivity

class WalkThroughActivity : AppCompatActivity() {

    private var walkThroughPagerAdapter : WalkThroughPagerAdapter? = null

    lateinit var binding: ActivityWalkThroughBinding
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_walk_through)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_walk_through)
        sharedPreferences = Constants.getSharedPreferences(this)


        walkThroughPagerAdapter = WalkThroughPagerAdapter(supportFragmentManager)

        binding.container.adapter = walkThroughPagerAdapter


        binding.skipTextView.setOnClickListener(View.OnClickListener {
            Constants.changeActivity<LoginActivity>(this)
            sharedPreferences.edit().putBoolean(Constants.IS_FIRST_TIME, false).apply()
        })

        binding.container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        binding.indicatorImageView1.setBackgroundResource(R.drawable.circle_dark_grey_backgraount)
                        binding.indicatorImageView2.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                        binding.indicatorImageView3.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                    }
                    1 -> {
                        binding.indicatorImageView1.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                        binding.indicatorImageView2.setBackgroundResource(R.drawable.circle_dark_grey_backgraount)
                        binding.indicatorImageView3.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                    }
                    2 -> {
                        binding.indicatorImageView1.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                        binding.indicatorImageView2.setBackgroundResource(R.drawable.circle_light_grey_backgraount)
                        binding.indicatorImageView3.setBackgroundResource(R.drawable.circle_dark_grey_backgraount)
                    }
                }
            }

        })
    }

}
