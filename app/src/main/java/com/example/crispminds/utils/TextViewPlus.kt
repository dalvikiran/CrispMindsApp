package com.example.crispminds.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.example.crispminds.R

/**
 * Summary
 * This java file is used for customizing the android textview
 */

@SuppressLint("AppCompatCustomView")
class TextViewPlus : TextView {
    constructor(context: Context?) : super(context) {}
    // Constructor for passing the attribute
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setCustomFont(context, attrs)
    }

    // Constructor for passing the attribute and def style
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        setCustomFont(context, attrs)
    }

    //    Set the custom font to the textview using the xml attribute
    private fun setCustomFont(
        ctx: Context,
        attrs: AttributeSet?
    ) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus)
        val customFont = a.getString(R.styleable.TextViewPlus_customFont)
        setCustomFont(ctx, customFont)
        a.recycle()
    }

    //    this method is used to get which font should be applied to the textview
    fun setCustomFont(ctx: Context, asset: String?): Boolean {
        var tf: Typeface? = null
        tf = try {
            Typeface.createFromAsset(ctx.assets, asset)
        } catch (e: Exception) {
            Log.e(
                "Tag",
                "Could not get typeface: " + e.message
            )
            return false
        }
        setTypeface(tf)
        return true
    }

    companion object {
        private const val TAG = "TextView"
    }
}