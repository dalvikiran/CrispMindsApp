package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.SpecializationCellLayoutBinding

class CoachSpecializationAdapter() : RecyclerView.Adapter<CoachSpecializationAdapter.CoachSpecializationViewHolder>() {

    var specializationList = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachSpecializationViewHolder {
        return CoachSpecializationViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return specializationList.size
    }

    override fun onBindViewHolder(holder: CoachSpecializationViewHolder, position: Int) {
        val item = specializationList.get(position)
        holder.bind(item)
    }

    class CoachSpecializationViewHolder(val binding : SpecializationCellLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(specialization : String) {

            binding.specialization = specialization

        }

        companion object {
            fun from(parent: ViewGroup): CoachSpecializationViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SpecializationCellLayoutBinding.inflate(layoutInflater, parent, false)
                return CoachSpecializationViewHolder(binding)
            }
        }
    }

}