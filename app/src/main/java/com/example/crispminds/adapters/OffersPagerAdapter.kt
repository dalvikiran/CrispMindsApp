package com.example.crispminds.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.crispminds.view.dashboard.fragments.OffersSliderCellFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class OffersPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    var offersList = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a OffersSliderCellFragment (defined as a static inner class below).
        return OffersSliderCellFragment.newInstance(position ,offersList)
    }

    override fun getCount(): Int {
        // Show 5 total pages.(we will use 5 pages so change it to 5)
        return offersList.size
    }
}
