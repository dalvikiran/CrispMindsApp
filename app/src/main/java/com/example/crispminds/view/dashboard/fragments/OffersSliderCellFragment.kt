package com.example.crispminds.view.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.crispminds.R
import kotlinx.android.synthetic.main.offers_slider_cell_layout.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class OffersSliderCellFragment(var sliderList : List<Int>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.offers_slider_cell_layout, container, false)


         /*if (sliderList.get(arguments!!.getInt(ARG_SECTION_NUMBER)) != null){
         }*/
        rootView.image_iv.setImageResource(sliderList.get(arguments!!.getInt(ARG_SECTION_NUMBER)))
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int,sliderList : List<Int>): OffersSliderCellFragment {
            val fragment = OffersSliderCellFragment(sliderList)
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber )
            fragment.arguments = args
            return fragment
        }
    }
}

