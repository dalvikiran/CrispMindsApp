package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.JournalItemCellLayoutBinding
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.utils.Constants

class ProfileOfferInterestedListAdapter:
    RecyclerView.Adapter<ProfileOfferInterestedListAdapter.ProfileOfferInterestedViewHolder>() {

    var profileOfferInterestedList = listOf<JournalMainCategoryDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOfferInterestedViewHolder {
        return ProfileOfferInterestedViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return profileOfferInterestedList.size
    }

    override fun onBindViewHolder(holder: ProfileOfferInterestedViewHolder, position: Int) {
        val item = profileOfferInterestedList[position]
        holder.bind(position, item)
    }

    class ProfileOfferInterestedViewHolder(val binding: JournalItemCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            journalItemDetails: JournalMainCategoryDTO
        ) {

            binding.journalDescTxt.maxLines = 3

            binding.showDataImg.setOnClickListener {
                binding.editOrDeleteView.visibility = View.VISIBLE
                binding.hideDataImg.visibility = View.VISIBLE
                binding.showDataImg.visibility = View.GONE
                binding.journalDescTxt.maxLines = Integer.MAX_VALUE
            }

            binding.hideDataImg.setOnClickListener {
                binding.editOrDeleteView.visibility = View.GONE
                binding.hideDataImg.visibility = View.GONE
                binding.showDataImg.visibility = View.VISIBLE
                binding.journalDescTxt.maxLines = 3
            }


            binding.hideDataImg.performClick()

            if (journalItemDetails.acf != null) {
                journalItemDetails.acf!!.journal_date_dd =
                    Constants.getFormattedDD(journalItemDetails.acf!!.journal_date)
                journalItemDetails.acf!!.journal_date_day =
                    Constants.getFormattedDayOfWeek(journalItemDetails.acf!!.journal_date)
            }
            binding.journalDataDTO = journalItemDetails.acf

        }

        companion object {
            fun from(parent: ViewGroup): ProfileOfferInterestedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = JournalItemCellLayoutBinding.inflate(layoutInflater, parent, false)
                return ProfileOfferInterestedViewHolder(binding)
            }
        }
    }

}