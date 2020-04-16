package com.example.crispminds.view.dashboard.fragments


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.example.crispminds.R
import com.example.crispminds.adapters.SessionAndSubscribeListAdapter
import com.example.crispminds.databinding.FragmentBookSessionBinding
import com.example.crispminds.models.Meeting
import com.example.crispminds.models.CoachDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.view_model_factory.CoachDetailsViewModelFactory
import com.example.crispminds.view.dashboard.viewmodel.BookSessionFragmentViewModel

/**
 * A simple [Fragment] subclass.
 */
class BookSessionFragment : Fragment(),SessionAndSubscribeListAdapter.OnSessionItemClickListener {


    lateinit var binding : FragmentBookSessionBinding
    lateinit var viewModelFactory : CoachDetailsViewModelFactory
    lateinit var coachDetails : CoachDTO
    lateinit var viewModel : BookSessionFragmentViewModel

    lateinit var bookSessionAdapter: SessionAndSubscribeListAdapter
    lateinit var subscibePlanAdapter: SessionAndSubscribeListAdapter

    var progressBar = CustomProgressBar()

//    var context = getContext()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        coachDetails = arguments?.get(Constants.COACH_DETAILS) as CoachDTO


        binding = DataBindingUtil.inflate<FragmentBookSessionBinding>(inflater,R.layout.fragment_book_session,
                        container,false)

        viewModelFactory = CoachDetailsViewModelFactory(context!!,coachDetails)
        viewModel = ViewModelProviders.of(this,viewModelFactory).
            get(BookSessionFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        bookSessionAdapter = SessionAndSubscribeListAdapter(Constants.BOOK_SESSION,this)
        binding.bookSessionRecyclerView.adapter = bookSessionAdapter
        bookSessionAdapter.bookAMeetingDataList = coachDetails.meetings!!

        subscibePlanAdapter = SessionAndSubscribeListAdapter(Constants.SUBSCRIBE_PLAN,this)
        binding.subscribePlanRecyclerView.adapter = subscibePlanAdapter
        subscibePlanAdapter.bookAMeetingDataList = coachDetails.meetings!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

        viewModel.closeFragment.observe(this, Observer {
            if (it){
                var bundle = bundleOf(Constants.COACH_DETAILS to coachDetails)
                binding.root.findNavController().navigate(R.id.action_bookSessionFragment_to_coachDetailsFragment,bundle)
            }
        })
    }

    override fun onBookSessionClick(type : String, bookAMeeting: Meeting, isSelected : Boolean) {
        viewModel.onSessionSelected(type, bookAMeeting,isSelected)

    }
}
