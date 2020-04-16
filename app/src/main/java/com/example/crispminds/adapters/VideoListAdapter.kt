package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.R
import com.example.crispminds.databinding.VideoCellLayoutBinding
import com.example.crispminds.models.VideoDetailDTO
import com.example.crispminds.models.categorydtos.VideoCategoryDTO
import com.example.crispminds.models.categorydtos.VideoDataDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import kotlinx.android.synthetic.main.video_cell_layout.view.*

class VideoListAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    var videoList = listOf<VideoMasterDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = videoList.get(position)
//        item.
        holder.bind(item, clickListener)
    }

    class VideoViewHolder(val binding: VideoCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoDetails: VideoMasterDTO, itemClick: OnItemClickListener) {

            binding.videoDetails = videoDetails
            binding.mainLayout.setOnClickListener {
                videoDetails?.let { it1 -> itemClick.onItemClick(it1) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): VideoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VideoCellLayoutBinding.inflate(layoutInflater, parent, false)
                return VideoViewHolder(binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(videoDetail: VideoMasterDTO)
    }
}