package com.example.crispminds.view.dashboard.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.example.crispminds.R
import com.example.crispminds.adapters.OffersAdapter
import com.example.crispminds.adapters.OffersPagerAdapter
import com.example.crispminds.databinding.FragmentHomeBinding
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.dashboard.viewmodel.HomeFragmentViewModel
import com.example.crispminds.view.mysession.MySessionActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    lateinit var viewModel : HomeFragmentViewModel

    private lateinit var mOffersAdapter: OffersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home,
            container,false)

        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        binding.homeViewModel = viewModel

        mOffersAdapter = OffersAdapter(activity!!)
        binding.offerViewPager.adapter = mOffersAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDashboardData()

        viewModel._offerList.observe(this, Observer {
            it?.let {
                mOffersAdapter!!.offersList = it
            }
        })

        onClickListeners()
    }

    fun onClickListeners(){

        viewModel.onVideoClickEvent.observeChange(this, Observer {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_videoFragment)

        })

        viewModel.onCoachesClickEvent.observeChange(this, Observer {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_coachesFragment)

        })

        viewModel.onSessionClickEvent.observeChange(this, Observer {
            Constants.changeActivity<MySessionActivity>(binding.root.context)

        })

        viewModel.onJournalClickEvent.observeChange(this, Observer {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_journalFragment)

        })

        viewModel.onRewardsClickEvent.observeChange(this, Observer {
            binding.root.findNavController().navigate(R.id.action_homeFragment_to_rewardsFragment)

        })
    }

}
