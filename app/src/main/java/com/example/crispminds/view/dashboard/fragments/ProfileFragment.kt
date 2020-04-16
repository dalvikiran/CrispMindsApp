package com.example.crispminds.view.dashboard.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.example.crispminds.R
import com.example.crispminds.adapters.ProfileFamilyMembersListAdapter
import com.example.crispminds.adapters.SubCategoryListAdapter
import com.example.crispminds.databinding.FragmentProfileBinding
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.ProfileFragmentViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(),SubCategoryListAdapter.OnSubCategoryItemClickListener {

    private lateinit var subCategoryListAdapter: SubCategoryListAdapter
    private lateinit var profileFamilyMembersListAdapter: ProfileFamilyMembersListAdapter
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false
        )
        initBinding()
        return binding.root
    }

    // Assigning the viewmodel and databinding
    private fun initBinding() {

        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            ProfileFragmentViewModel::class.java,
            this,
            binding.root
        )!!
        binding.profileViewModel = viewModel

        subCategoryListAdapter = SubCategoryListAdapter(this)
        binding.profileOffersInterestedRecyclerView.adapter = subCategoryListAdapter

        profileFamilyMembersListAdapter = ProfileFamilyMembersListAdapter()
        binding.profileFamilyMembersRecyclerView.adapter = profileFamilyMembersListAdapter


    }

    override fun onSubCategoryClick(categoryDTO: CategoryDTO) {

    }

}
