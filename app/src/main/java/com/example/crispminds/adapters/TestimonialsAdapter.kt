package com.example.crispminds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.crispminds.R
import com.example.crispminds.databinding.TestimonialCellLayoutBinding
import com.example.crispminds.models.Testimonial
import kotlinx.android.synthetic.main.fragment_coach_detail.view.*
import kotlinx.android.synthetic.main.offers_slider_cell_layout.view.*

class TestimonialsAdapter(private val context: Context) : PagerAdapter() {

    private var inflater: LayoutInflater? = null
    lateinit var binding : TestimonialCellLayoutBinding
    var testimonialsList = listOf<Testimonial>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return testimonialsList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater!!.inflate(R.layout.testimonial_cell_layout, null)

        binding = DataBindingUtil.inflate<TestimonialCellLayoutBinding>(inflater!!,R.layout.testimonial_cell_layout,
            container,false)

        binding.testimonial = testimonialsList.get(position)
//        binding.testimonialTextView.setText(testimonialsList.get(position).testimonials)

        val vp = container as ViewPager
        vp.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}