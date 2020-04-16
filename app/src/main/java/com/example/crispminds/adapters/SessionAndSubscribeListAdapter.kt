package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.SessionAndSubscribeCellLayoutBinding
import com.example.crispminds.models.Meeting

class SessionAndSubscribeListAdapter(private val type : String, private val clickListener: OnSessionItemClickListener) :
    RecyclerView.Adapter<SessionAndSubscribeListAdapter.SessionAndSubscribeViewHolder>() {

    var bookAMeetingDataList = listOf<Meeting>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionAndSubscribeViewHolder {
        return SessionAndSubscribeViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return bookAMeetingDataList.size
    }

    override fun onBindViewHolder(holder: SessionAndSubscribeViewHolder, position: Int) {
        val item = bookAMeetingDataList.get(position)
        holder.bind(type, item, clickListener)
    }

    class SessionAndSubscribeViewHolder(val binding : SessionAndSubscribeCellLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(type : String, bookAMeeting: Meeting, itemClick : OnSessionItemClickListener) {

            binding.bookAMeeting = bookAMeeting
            binding.checkBox.setOnClickListener{
                itemClick.onBookSessionClick(type, bookAMeeting,binding.checkBox.isChecked)
            }
        }

        companion object {
            fun from(parent: ViewGroup): SessionAndSubscribeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SessionAndSubscribeCellLayoutBinding.inflate(layoutInflater, parent, false)
                return SessionAndSubscribeViewHolder(binding)
            }
        }
    }

    interface OnSessionItemClickListener {
        fun onBookSessionClick(type : String, bookAMeeting: Meeting, selected : Boolean)
    }
}