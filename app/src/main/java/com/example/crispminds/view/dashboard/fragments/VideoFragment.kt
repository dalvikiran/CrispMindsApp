package com.example.crispminds.view.dashboard.fragments


import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.crispminds.R
import com.example.crispminds.adapters.SubCategoryListAdapter
import com.example.crispminds.adapters.VideoListAdapter
import com.example.crispminds.databinding.FragmentVideoBinding
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.Constants.Companion.VIDEO_DETAILS
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.VideoFragmentViewModel

/**
 * A simple [Fragment] subclass.
 */
class VideoFragment : Fragment(),
    VideoListAdapter.OnItemClickListener, SubCategoryListAdapter.OnSubCategoryItemClickListener {


    lateinit var binding: FragmentVideoBinding
    lateinit var viewModel: VideoFragmentViewModel
    lateinit var subCategoryAdapter: SubCategoryListAdapter
    lateinit var videoListAdapter: VideoListAdapter
    lateinit var sharedPreferences: SharedPreferences
    private var isSearchClicked = false
    var videoCategoryId: Long? = 0
    var progressBar = CustomProgressBar()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentVideoBinding>(
            inflater, R.layout.fragment_video,
            container, false
        )
//        viewModel = ViewModelProviders.of(this).get(VideoFragmentViewModel::class.java)


        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            VideoFragmentViewModel::class.java,
            this,
            binding.root
        )!!

//        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        subCategoryAdapter = SubCategoryListAdapter(this)
        binding.videoSubCategoryRecyclerView.adapter = subCategoryAdapter

        videoListAdapter = VideoListAdapter(this)
        binding.videoRecyclerView.adapter = videoListAdapter
//        videoListAdapter.coachList = viewModel.getVideoData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = Constants.getSharedPreferences(context!!)
        videoCategoryId = Constants.decrypt(
            sharedPreferences.getString(Constants.VIDEO_CATEGORY_ID, "")
        )?.toLong()

//        viewModel.getVideoSubCategoryData(videoCategoryId!!)
        videoCategoryId?.let { viewModel.fetchVideosListByCategoryId(it) }

        viewModelObserver()

    }

    override fun onItemClick(videoDetail: VideoMasterDTO) {
//        Toast.makeText(context,"${videoDetail.title} clicked", Toast.LENGTH_LONG).show()
        var bundle = bundleOf(VIDEO_DETAILS to videoDetail)
        binding.root.findNavController()
            .navigate(R.id.action_videoFragment_to_videoDetailFragment, bundle)
    }

    override fun onSubCategoryClick(categoryDTO: CategoryDTO) {
//        viewModel.showProgressBar.value = true
        viewModel.getVideoData(videoCategoryId!!, categoryDTO.categoryId!!)

    }

    fun viewModelObserver() {

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })

        viewModel.videoSubCategoryArrayList.observe(this, Observer {
            it?.let {
                subCategoryAdapter.subCategoryList = it
            }
        })

        viewModel.updatedVideoArrayList.observe(this, Observer {
            it?.let {
                videoListAdapter.videoList = it
            }
        })



        viewModel.searchQueryString.observe(this, Observer {

            val query = viewModel.searchQueryString.value
            if (query?.length!! >= 3) {
                isSearchClicked = true
                viewModel.searchByText(query)
            } else if (isSearchClicked && query.isEmpty()) {
                isSearchClicked = false
                viewModel.populateData(viewModel.originalVideoList)
            }
        })


    }

}

