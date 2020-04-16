package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.JournalItemCellLayoutBinding
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.utils.Constants

class JournalListAdapter(
    private val clickListener: OnItemClickListener,
    private val deleteClickListener: OnItemDeleteClickListener
) :
    RecyclerView.Adapter<JournalListAdapter.JournalViewHolder>() {

    var journalList = listOf<JournalMainCategoryDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        return JournalViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val item = journalList[position]
        holder.bind(position, item, clickListener, deleteClickListener)
    }

    class JournalViewHolder(val binding: JournalItemCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun addLayout(){

        }

        fun bind(
            position: Int,
            journalItemDetails: JournalMainCategoryDTO,
            itemClick: OnItemClickListener,
            itemDeleteClick: OnItemDeleteClickListener
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

            binding.edit.setOnClickListener {
                itemClick.onItemEditClick(position,journalItemDetails)
            }

            binding.delete.setOnClickListener {
                itemDeleteClick.onItemDeleteClick(position,journalItemDetails)
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
            fun from(parent: ViewGroup): JournalViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = JournalItemCellLayoutBinding.inflate(layoutInflater, parent, false)
                return JournalViewHolder(binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemEditClick(position: Int, journalItemDetail: JournalMainCategoryDTO)
    }

    interface OnItemDeleteClickListener {
        fun onItemDeleteClick(position: Int, journalItemDetail: JournalMainCategoryDTO)
    }
}