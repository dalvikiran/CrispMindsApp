package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.CoachesCellLayoutBinding
import com.example.crispminds.models.CoachDTO

class CoachesListAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<CoachesListAdapter.CoachViewHolder>() {

    var coachList = listOf<CoachDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        return CoachViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        val item = coachList.get(position)
        holder.bind(item, clickListener)
    }

    class CoachViewHolder(val binding : CoachesCellLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(coachDetails : CoachDTO, itemClick : OnItemClickListener) {

            binding.coachDetails = coachDetails
            binding.mainLayout.setOnClickListener{
                itemClick.onItemClick(coachDetails)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CoachViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoachesCellLayoutBinding.inflate(layoutInflater, parent, false)
                return CoachViewHolder(binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(coachDetail: CoachDTO)
    }
}