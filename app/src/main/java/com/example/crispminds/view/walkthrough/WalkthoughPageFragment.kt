package com.example.kotlin_example.walk_through

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.crispminds.R
import com.example.crispminds.databinding.WalkThroughScreen1Binding
import com.example.crispminds.databinding.WalkThroughScreen2Binding
import com.example.crispminds.databinding.WalkThroughScreen3Binding
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.userlogin.LoginActivity

/**
 * A placeholder fragment containing a simple view.
 */
class WalkthoughPageFragment() : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = Constants.getSharedPreferences(activity!!.applicationContext)

        var rootView : View? = null
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 1) {

            val binding = DataBindingUtil.inflate<WalkThroughScreen1Binding>(inflater,R.layout.walk_through_screen_1,
                container,false)


            rootView = binding.root
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 2){
            val binding = DataBindingUtil.inflate<WalkThroughScreen2Binding>(inflater,R.layout.walk_through_screen_2,
                container,false)

            rootView = binding.root
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 3){
            val binding = DataBindingUtil.inflate<WalkThroughScreen3Binding>(inflater,R.layout.walk_through_screen_3,
                container,false)

            binding.getStartedButton.setOnClickListener(View.OnClickListener {
                Constants.changeActivity<LoginActivity>(activity!!.applicationContext)
                sharedPreferences.edit().putBoolean(Constants.IS_FIRST_TIME, false).apply()
            })
            rootView = binding.root
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): WalkthoughPageFragment {
            val fragment = WalkthoughPageFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}

