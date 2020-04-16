package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.SubCategoryCellLayoutBinding
import com.example.crispminds.models.categorydtos.CategoryDTO

class SubCategoryListAdapter(private val clickListenerSub: OnSubCategoryItemClickListener) :
    RecyclerView.Adapter<SubCategoryListAdapter.SubCategoryViewHolder>() {

    var subCategoryList = listOf<CategoryDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        return SubCategoryViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = subCategoryList.get(position)
        holder.bind(item, clickListenerSub)
    }

    class SubCategoryViewHolder(val binding: SubCategoryCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            categoryDTO: CategoryDTO,
            itemClickSub: OnSubCategoryItemClickListener
        ) {

            binding.category = categoryDTO
            binding.mainLayout.setOnClickListener {
                itemClickSub.onSubCategoryClick(categoryDTO)
            }
        }

        companion object {
            fun from(parent: ViewGroup): SubCategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SubCategoryCellLayoutBinding.inflate(layoutInflater, parent, false)
                return SubCategoryViewHolder(binding)
            }
        }
    }

    interface OnSubCategoryItemClickListener {
        fun onSubCategoryClick(categoryDTO: CategoryDTO)
    }
}