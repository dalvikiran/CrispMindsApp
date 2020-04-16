package com.example.crispminds.view.dashboard.fragments


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.example.crispminds.R
import com.example.crispminds.adapters.JournalDateListAdapter
import com.example.crispminds.adapters.JournalListAdapter
import com.example.crispminds.databinding.FragmentJournalBinding
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.JournalFragmentViewModel

/**
 * A simple [Fragment] subclass.
 */
class JournalFragment : Fragment(), JournalListAdapter.OnItemClickListener,
    JournalListAdapter.OnItemDeleteClickListener,
    JournalDateListAdapter.OnItemClickListener {

    lateinit var mJournalFragmentViewModel: JournalFragmentViewModel // View Model
    lateinit var binding: FragmentJournalBinding // DataUiBinding
    lateinit var journalListAdapter: JournalListAdapter
    lateinit var journalDateListAdapter: JournalDateListAdapter
    var position: Int = 0
    private var progressBar = CustomProgressBar()
    private var isSearchClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentJournalBinding>(
            inflater, R.layout.fragment_journal, container, false
        )

        initBinding()

        return binding.root
    }

    // Assigning the viewmodel and databinding
    private fun initBinding() {

        mJournalFragmentViewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            JournalFragmentViewModel::class.java,
            this,
            binding.root
        )!!
        binding.journalViewModel = mJournalFragmentViewModel

        journalListAdapter = JournalListAdapter(this, this)
        binding.journalRecyclerView.adapter = journalListAdapter

        journalDateListAdapter = JournalDateListAdapter(this)
        binding.journalDateListRecyclerView.adapter = journalDateListAdapter


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mJournalFragmentViewModel.journalMutableLiveDataList.observe(this, Observer {
            it?.let {
                journalListAdapter.journalList = it
            }
        })

        mJournalFragmentViewModel.listOfDatesSet.observe(this, Observer {
            it?.let {
                journalDateListAdapter.journalDateList = it
            }
        })

        binding.filterImageView.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_journalFragment_to_journalEntryFragment)
        }

        // Search functionality
        mJournalFragmentViewModel.journalSearchQueryString.observe(this, Observer {

            val query = mJournalFragmentViewModel.journalSearchQueryString.value
            if (query?.length!! >= 3) {
                isSearchClicked = true
                mJournalFragmentViewModel.searchByText(query)
            } else if (isSearchClicked && query.isEmpty()) {
                isSearchClicked = false
                mJournalFragmentViewModel.maintainJournalData()
            }
        })


        mJournalFragmentViewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

    }


    override fun onItemEditClick(position: Int, journalItemDetail: JournalMainCategoryDTO) {

        this.position = position
        if (journalItemDetail.acf != null) {
            journalItemDetail.acf!!.journal_smiley_id = mJournalFragmentViewModel.journalSmileId
            journalItemDetail.acf!!.journal_smiley_name = mJournalFragmentViewModel.journalSmileName
        }
//        journalItemDetail.acf.journal_category_List = mJournalFragmentViewModel.categoryArrayList

        val bundle = bundleOf(Constants.JOURNAL_DETAILS to journalItemDetail)
        binding.root.findNavController()
            .navigate(R.id.action_journalFragment_to_journalEntryFragment, bundle)

    }


    override fun onItemDeleteClick(position: Int, journalItemDetail: JournalMainCategoryDTO) {
        this.position = position
        mJournalFragmentViewModel.onDeleteItem(position, journalItemDetail)
    }

    override fun onDateItemClick(journalDateItemString: String) {
        mJournalFragmentViewModel.populateDataByDate(journalDateItemString)
//        Log.e("journalDateItemDetail", journalDateItemString)
    }
}
