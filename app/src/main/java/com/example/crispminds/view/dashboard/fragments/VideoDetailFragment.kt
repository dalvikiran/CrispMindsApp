package com.example.crispminds.view.dashboard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.crispminds.R
import com.example.crispminds.databinding.FragmentVideoDetailBinding
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.VideoDetailsFragmentViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

/**
 * A simple [Fragment] subclass.
 */
class VideoDetailFragment : Fragment() {

    lateinit var viewModel: VideoDetailsFragmentViewModel
    lateinit var binding: FragmentVideoDetailBinding
    lateinit var videoDetail: VideoMasterDTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_video_detail, container, false)

        // Binding and updating UI
        binding = DataBindingUtil.inflate<FragmentVideoDetailBinding>(
            inflater,
            R.layout.fragment_video_detail,
            container,
            false
        )
        videoDetail = arguments?.get(Constants.VIDEO_DETAILS) as VideoMasterDTO

//        viewModel = ViewModelProviders.of(this).get(VideoFragmentViewModel::class.java)
        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            VideoDetailsFragmentViewModel::class.java,
            this,
            binding.root
        )!!

        binding.viewModel = viewModel

//        var videoDetail = arguments?.get(Constants.VIDEO_DETAILS)

//        Log.e("videoDetail", videoDetail.toString())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.youtubePlayerView.initialize(YouTubePlayerListener)
        initYouTubePlayerView()
//        viewModel.videoData = videoDetail
        viewModel.videoMasterDTO = videoDetail
    }

    fun initYouTubePlayerView() {
        lifecycle.addObserver(binding.youtubePlayerView)


        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoDetail.videoLinkId?.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        it,
                        0f
                    )
                }
//                youTubePlayer.loadOrCueVideo(
//                    lifecycle,
//                    videoDetail.youtube_video_link!!,
//                    0f
//                )
            }
        })
    }

    override fun onStop() {
        super.onStop()
        binding.youtubePlayerView.destroyDrawingCache()
    }
}
