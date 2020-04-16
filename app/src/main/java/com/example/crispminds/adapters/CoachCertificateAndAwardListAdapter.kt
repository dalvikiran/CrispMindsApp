package com.example.crispminds.adapters

import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.CertificateAndAwardCellLayoutBinding
import com.example.crispminds.databinding.VideoThumbnailLayoutBinding
import com.example.crispminds.models.CertificationAndAwards
import com.example.crispminds.models.VideoDTO
import com.example.crispminds.utils.Constants

class CoachCertificateAndAwardListAdapter(private val clickListener: OnItemClickListener) :
        RecyclerView.Adapter<CoachCertificateAndAwardListAdapter.CoachCertificateViewHolder>() {

    var certificateList = listOf<CertificationAndAwards>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachCertificateViewHolder {
        return CoachCertificateViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return certificateList.size
    }

    override fun onBindViewHolder(holder: CoachCertificateViewHolder, position: Int) {
        val item = certificateList.get(position)
        holder.bind(item, clickListener)
    }

    class CoachCertificateViewHolder(val binding : CertificateAndAwardCellLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(certificateDetails : CertificationAndAwards, itemClick : OnItemClickListener) {

            binding.certificateDetail = certificateDetails
            binding.certificateLayout.setOnClickListener{
//                Log.e("certificateLayout", "click")
                itemClick.onCertificateClick(certificateDetails)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CoachCertificateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CertificateAndAwardCellLayoutBinding.inflate(layoutInflater, parent, false)
                return CoachCertificateViewHolder(binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onCertificateClick(certificateDetails : CertificationAndAwards)
    }
}