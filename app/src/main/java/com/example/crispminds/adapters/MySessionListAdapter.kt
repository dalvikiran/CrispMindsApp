package com.example.crispminds.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crispminds.R
import com.example.crispminds.databinding.MySessionCellLayoutBinding
import com.example.crispminds.databinding.MySessionSubscriptionCellLayoutBinding
import com.example.crispminds.models.MySessionDTO
import com.example.crispminds.models.MySessionMainDTO

class MySessionListAdapter(
    private val clickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MySessionListAdapter.MySessionViewHolder>() {

    lateinit var mActivity: Activity

    var mySessionList = listOf<MySessionMainDTO>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setActivity(activity: Activity) {
        mActivity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySessionViewHolder {
        return MySessionViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return mySessionList.size
    }

    override fun onBindViewHolder(holder: MySessionViewHolder, position: Int) {
        val item = mySessionList[position]
        holder.bind(position, item, clickListener)
        holder.addSubscriptionLayout(mActivity, item.acf?.list)
    }

    class MySessionViewHolder(val binding: MySessionCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            mySessionItemDetails: MySessionMainDTO,
            itemClick: OnItemClickListener

        ) {
            binding.subscriptionLayout.visibility = View.GONE
            binding.mySessionDTO = mySessionItemDetails.acf

            binding.subscriptionPlanTxtLayout.setOnClickListener {

                if (binding.subscriptionLayout.visibility == View.GONE) {
                    binding.subscriptionLayout.visibility = View.VISIBLE
                }else{
                    binding.subscriptionLayout.visibility = View.GONE
                }

            }
        }

        companion object {
            fun from(
                parent: ViewGroup
            ): MySessionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                var binding = MySessionCellLayoutBinding.inflate(layoutInflater, parent, false)
                return MySessionViewHolder(binding)
            }
        }


        fun addSubscriptionLayout(
            activity: Activity,
            mySessionList: List<MySessionDTO>?
        ) {

            if (mySessionList != null && mySessionList.isNotEmpty()) {

                for (item in mySessionList) {
                    val mySessionSubscriptionLayout =
                        MySessionSubscriptionLayout(activity, item, binding)
                    binding.subscriptionLayout.addView(mySessionSubscriptionLayout)
                }
            }


        }

    }

    interface OnItemClickListener {
        fun onItemEditClick(position: Int, mySessionItemDetail: MySessionMainDTO)
    }


    class MySessionSubscriptionLayout(
        activity: Activity?,
        mySessionDTO: MySessionDTO?,
        mySessionCellLayoutBinding: MySessionCellLayoutBinding
    ) : LinearLayout(activity) {


        var mySessionSubscriptionCellLayoutBinding: MySessionSubscriptionCellLayoutBinding

        init {
            mySessionSubscriptionCellLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.my_session_subscription_cell_layout,
                mySessionCellLayoutBinding.subscriptionLayout,
                true
            )

            if (mySessionDTO != null) {

                mySessionSubscriptionCellLayoutBinding.mySessionDTO = mySessionDTO
            }

        }


    }


}