package com.example.crispminds.view.questionnaire.frgments


import android.content.SharedPreferences
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.crispminds.R
import com.example.crispminds.databinding.FragmentAreaOfInterestBinding
import com.example.crispminds.databinding.FragmentBookSessionBinding
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.view_model_factory.CoachDetailsViewModelFactory
import com.example.crispminds.view.dashboard.DashboardActivity

/**
 * A simple [Fragment] subclass.
 */
class AreaOfInterestFragment : Fragment() {


    lateinit var binding : FragmentAreaOfInterestBinding
//    lateinit var viewModelFactory : CoachDetailsViewModelFactory
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = Constants.getSharedPreferences(activity!!.applicationContext)

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_area_of_interest, container, false)
        binding = DataBindingUtil.inflate<FragmentAreaOfInterestBinding>(inflater,R.layout.fragment_area_of_interest,
            container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.setOnClickListener{
            binding.root.findNavController()
                .navigate(R.id.action_areaOfInterestFragment_to_servicesToInterestFragment)
        }

        binding.skipTextView.setOnClickListener{
            sharedPreferences.edit().putBoolean(Constants.IS_QUETIONAIRE_DONE, true).apply()
            Constants.changeActivity<DashboardActivity>(activity!!.applicationContext)
        }
    }

}
