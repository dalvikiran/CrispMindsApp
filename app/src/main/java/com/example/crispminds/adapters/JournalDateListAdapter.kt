package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.JournalDateItemCellLayoutBinding
import com.example.crispminds.models.categorydtos.journal.JournalDataDTO

class JournalDateListAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<JournalDateListAdapter.JournalViewHolder>() {

    var journalDateList = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        return JournalViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return journalDateList.size
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val item = journalDateList.get(position)
        holder.bind(item, clickListener)
    }

    class JournalViewHolder(val binding: JournalDateItemCellLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(journalItemDetails: String, itemClick: OnItemClickListener) {

            binding.dateString = journalItemDetails
            binding.dateTxt.setOnClickListener {
                itemClick.onDateItemClick(journalItemDetails)
            }
//            binding.dateTxt.text = binding.journalDataDTO.journal_date.toString()

        }

        companion object {
            fun from(parent: ViewGroup): JournalViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    JournalDateItemCellLayoutBinding.inflate(layoutInflater, parent, false)
                return JournalViewHolder(binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onDateItemClick(journalDateItemString: String)
    }
}