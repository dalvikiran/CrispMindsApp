package com.example.crispminds.view.dashboard.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.crispminds.R
import com.example.crispminds.adapters.OffersAdapter
import com.example.crispminds.adapters.OffersPagerAdapter
import com.example.crispminds.databinding.FragmentHomeBinding
import com.example.crispminds.databinding.FragmentRewardsBinding

/**
 * A simple [Fragment] subclass.
 */
class RewardsFragment : Fragment() {

    lateinit var binding : FragmentRewardsBinding

    private lateinit var parentingOffersAdapter: OffersAdapter
    private lateinit var financeOffersAdapter: OffersAdapter
    private lateinit var utilizeYourOffersAdapter: OffersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_rewards, container, false)

        binding = DataBindingUtil.inflate<FragmentRewardsBinding>(inflater, R.layout.fragment_rewards,
            container,false)

        parentingOffersAdapter = OffersAdapter(activity!!)
        binding.parentingOffersViewPager.adapter = parentingOffersAdapter

        financeOffersAdapter = OffersAdapter(activity!!)
        binding.financeOffersViewPager.adapter = financeOffersAdapter

        utilizeYourOffersAdapter = OffersAdapter(activity!!)
        binding.utilizeYourOffersViewPager.adapter = utilizeYourOffersAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var testimonialsList = listOf<Int>(R.drawable.offer_img_1,R.drawable.offer_img_2)
        parentingOffersAdapter.offersList = listOf<Int>(R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2)
        financeOffersAdapter.offersList = listOf<Int>(R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1)
        utilizeYourOffersAdapter.offersList = listOf<Int>(R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2,R.drawable.offer_img_1,R.drawable.offer_img_2)

    }
}
