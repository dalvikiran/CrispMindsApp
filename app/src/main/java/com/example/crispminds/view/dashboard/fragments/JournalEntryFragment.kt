package com.example.crispminds.view.dashboard.fragments


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.crispminds.R
import com.example.crispminds.adapters.SubCategoryListAdapter
import com.example.crispminds.databinding.FragmentJournalEntryBinding
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.JournalFragmentViewModel

/**
 * A simple [Fragment] subclass.
 */
class JournalEntryFragment : Fragment(), SubCategoryListAdapter.OnSubCategoryItemClickListener {

    lateinit var binding: FragmentJournalEntryBinding
    lateinit var viewModel: JournalFragmentViewModel
    lateinit var subCategoryAdapter: SubCategoryListAdapter
    private var progressBar = CustomProgressBar()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentJournalEntryBinding>(
            inflater,
            R.layout.fragment_journal_entry,
            container,
            false
        )
        initBinding()

        return binding.root
    }

    private fun initBinding() {

        // Binding and updating UI
        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            JournalFragmentViewModel::class.java,
            this,
            binding.root
        )!!
        binding.journalFragmentViewModel = viewModel


        val videoDetail = arguments?.get(Constants.JOURNAL_DETAILS) as JournalMainCategoryDTO?
        viewModel.populateJournalEntryData(videoDetail)

        subCategoryAdapter = SubCategoryListAdapter(this)
        binding.journalCategoryRecyclerView.adapter = subCategoryAdapter
        viewModel.getVideoSubCategoryData()

        viewModel.videoSubCategoryArrayList.observe(this, Observer {
            it?.let {
                subCategoryAdapter.subCategoryList = it
            }
        })


        viewModel.journalDetailsUpdated.observe(this, Observer {

            if (viewModel.journalDetailsUpdated.value == true) {
                (activity as Activity).onBackPressed()
            }

        })

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })


    }

    override fun onSubCategoryClick(categoryDTO: CategoryDTO) {
        viewModel.selectedCategoryId.value = categoryDTO.categoryId
    }

}
