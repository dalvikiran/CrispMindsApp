package com.example.kotlin_example.walk_through

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class WalkThroughPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a OffersSliderCellFragment (defined as a static inner class below).
        return WalkthoughPageFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        // Show 5 total pages.(we will use 5 pages so change it to 5)
        return 3
    }
}
