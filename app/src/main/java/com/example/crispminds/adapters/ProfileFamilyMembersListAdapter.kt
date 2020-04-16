package com.example.crispminds.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.databinding.ProfileFamilyMembersCellLayoutBinding
import com.example.crispminds.models.ProfileFamilyMembersDTO

class ProfileFamilyMembersListAdapter :
    RecyclerView.Adapter<ProfileFamilyMembersListAdapter.ProfileFamilyMembersViewHolder>() {

    var profileFamilyMembersList = listOf<ProfileFamilyMembersDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileFamilyMembersViewHolder {
        return ProfileFamilyMembersViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return profileFamilyMembersList.size
    }

    override fun onBindViewHolder(holder: ProfileFamilyMembersViewHolder, position: Int) {
        val item = profileFamilyMembersList[position]
        holder.bind(position, item)
    }

    class ProfileFamilyMembersViewHolder(val binding: ProfileFamilyMembersCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            profileFamilyMembersDTO: ProfileFamilyMembersDTO
        ) {


            binding.profileFamilyMembersDetailsDTO = profileFamilyMembersDTO.acf

        }

        companion object {
            fun from(parent: ViewGroup): ProfileFamilyMembersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProfileFamilyMembersCellLayoutBinding.inflate(layoutInflater, parent, false)
                return ProfileFamilyMembersViewHolder(binding)
            }
        }
    }

}