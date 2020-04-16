package com.example.crispminds.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.crispminds.R
import com.example.crispminds.databinding.VideoDialogLayoutBinding
import com.example.crispminds.models.VideoDTO
import com.example.crispminds.models.VideoMasterDTO
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class VideoDialog(videoDTO: VideoMasterDTO) : DialogFragment() {

    lateinit var binding : VideoDialogLayoutBinding
    val videoDTO : VideoMasterDTO
//    val context : Context

    init {
//        this.context = context
        this.videoDTO = videoDTO
        setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
            R.layout. video_dialog_layout, null, false)
//        setContentView(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initYouTubePlayerView()

        binding.closeClickImageView.setOnClickListener{
            dismiss()
        }

    }

    fun initYouTubePlayerView(){
         lifecycle.addObserver(binding.youtubePlayerView)

//        binding.youtubePlayerView.addYouTubePlayerListener(AbstractYouTubePlayerListener)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoDTO?.let {
                    youTubePlayer.loadOrCueVideo(
                        lifecycle,
                        it.videoLinkId!!,
                        0f
                    )
                }
            }
        })
    }

}