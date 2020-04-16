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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crispminds.R
import com.example.crispminds.adapters.CoachCertificateAndAwardListAdapter
import com.example.crispminds.adapters.CoachSpecializationAdapter
import com.example.crispminds.adapters.CoachVideoListAdapter
import com.example.crispminds.adapters.TestimonialsAdapter
import com.example.crispminds.databinding.FragmentCoachDetailBinding
import com.example.crispminds.models.*
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.view_model_factory.CoachDetailsViewModelFactory
import com.example.crispminds.view.dashboard.viewmodel.CoachDetailsFragmentViewModel
import com.example.crispminds.view.dialogs.CertificateDialog
import com.example.crispminds.view.dialogs.VideoDialog

/**
 * A simple [Fragment] subclass.
 */
class CoachDetailsFragment : Fragment(),
    CoachVideoListAdapter.OnVideoClickListener,
    CoachCertificateAndAwardListAdapter.OnItemClickListener {



    lateinit var binding : FragmentCoachDetailBinding
    lateinit var coachDetails : CoachDTO
    lateinit var viewModelFactory: CoachDetailsViewModelFactory
    lateinit var viewModel: CoachDetailsFragmentViewModel

    lateinit var coachVideoListAdapter : CoachVideoListAdapter
    lateinit var coachSpecializationAdapter : CoachSpecializationAdapter
    lateinit var coachCertificateListAdapter : CoachCertificateAndAwardListAdapter
    lateinit var testimonialsAdapter: TestimonialsAdapter

    var progressBar = CustomProgressBar()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_coach_detail, container, false)

        coachDetails = arguments?.get(Constants.COACH_DETAILS) as CoachDTO


        binding = DataBindingUtil.inflate<FragmentCoachDetailBinding>(inflater,
            R.layout.fragment_coach_detail, container,false)

        viewModelFactory = CoachDetailsViewModelFactory(activity!!.applicationContext,coachDetails)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
                        .get(CoachDetailsFragmentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoachDetails(coachDetails.coachId!!)

        binding.bookSessionLayout.setOnClickListener{
            var bundle = bundleOf(Constants.COACH_DETAILS to coachDetails)

            binding.root.findNavController().navigate(R.id.action_coachDetailsFragment_to_bookSessionFragment,bundle)

        }

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

        viewModel.coachDto.observe(this, Observer {
            if (it != null){
                coachDetails = it
                showData()
            }
        })
    }

    fun showData(){

        if (coachDetails.coachVideos != null){
            coachVideoListAdapter = CoachVideoListAdapter(this)
            binding.coachesVideosRecyclerView.adapter = coachVideoListAdapter
            coachVideoListAdapter.videoList = coachDetails.coachVideos!!    //NO data added
        }

        if (coachDetails.specializationNames != null) {
            coachSpecializationAdapter = CoachSpecializationAdapter()
            binding.coachesSpecializationRecyclerView.adapter = coachSpecializationAdapter
            binding.coachesSpecializationRecyclerView.layoutManager = GridLayoutManager(activity,3)
            coachSpecializationAdapter.specializationList = coachDetails.specializationNames!!
        }

        if (coachDetails.certification != null){
            coachCertificateListAdapter = CoachCertificateAndAwardListAdapter(this)
            binding.coachesCertificatesRecyclerView.adapter = coachCertificateListAdapter
            coachCertificateListAdapter.certificateList = coachDetails.certification!!
        }

        if (coachDetails.testimonials != null){
            testimonialsAdapter = TestimonialsAdapter(context!!)
            binding.testimonialViewPager.adapter = testimonialsAdapter
            testimonialsAdapter.testimonialsList = coachDetails.testimonials!!
        }

    }

    override fun onVideoClick(videoDTO: VideoMasterDTO) {

//        Constants.showVideoDialog(activity as Context)

        val transaction = activity!!.supportFragmentManager.beginTransaction()
        val videoDialog = VideoDialog(videoDTO)
        videoDialog.show(transaction,"Video")

    }

    override fun onCertificateClick(certificateDetails : CertificationAndAwards) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        val certificateDialog = CertificateDialog(certificateDetails)
        certificateDialog.show(transaction,"Certificate")
    }
}
