package com.example.crispminds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.crispminds.R
import kotlinx.android.synthetic.main.offers_slider_cell_layout.view.*

class OffersAdapter(private val context: Context) : PagerAdapter() {

    private var inflater: LayoutInflater? = null
    var offersList = listOf<Int>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return offersList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(R.layout.offers_slider_cell_layout, null)
        view.image_iv.setImageResource(offersList.get(position))

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}