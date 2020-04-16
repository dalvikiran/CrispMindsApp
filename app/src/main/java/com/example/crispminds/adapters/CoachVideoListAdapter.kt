package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.VideoThumbnailLayoutBinding
import com.example.crispminds.models.VideoDTO
import com.example.crispminds.models.VideoMasterDTO

class CoachVideoListAdapter(private val clickListener: OnVideoClickListener) : RecyclerView.Adapter<CoachVideoListAdapter.CoachVideoViewHolder>() {

    var videoList = listOf<VideoMasterDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachVideoViewHolder {
        return CoachVideoViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: CoachVideoViewHolder, position: Int) {
        val item = videoList.get(position)
        holder.bind(item, clickListener)
    }

    class CoachVideoViewHolder(val binding : VideoThumbnailLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(videoDetails : VideoMasterDTO, videoClick : OnVideoClickListener) {

            binding.videoDetail = videoDetails
            binding.videoLayout.setOnClickListener{
                videoClick.onVideoClick(videoDetails)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CoachVideoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VideoThumbnailLayoutBinding.inflate(layoutInflater, parent, false)
                return CoachVideoViewHolder(binding)
            }
        }
    }

    interface OnVideoClickListener {
        fun onVideoClick(videoDetails: VideoMasterDTO)
    }
}