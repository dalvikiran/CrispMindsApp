package com.example.crispminds.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.crispminds.retrofit_services.network_utils.GET_YOUTUBE_VIDEO_THUMBNAIL_URL
import com.example.crispminds.retrofit_services.network_utils.YOUTUBE_VIDEO_THUMBNAIL_IMAGE_NAME
import com.example.crispminds.utils.Constants.Companion.selectDate
import com.example.crispminds.utils.Constants.Companion.selectTime
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.net.URL
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.view.MotionEvent
import androidx.annotation.MainThread
import androidx.databinding.adapters.TextViewBindingAdapter.setText




class BindingAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
//        Glide.with(view.context).load("https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg").into(view)
                Glide.with(view.context)
                    .load(url)
//                    .centerCrop()
//                    .fitCenter()
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("backgroundUrl")
        fun loadBackgraoundImage(view: View, url: String?) {

            Thread {
                //Do some Network Request
                if (!url.isNullOrEmpty()) {
                    val url = URL(url)
                    val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    val image = BitmapDrawable(view.context.resources, bitmap)

                    var activity = view.context as Activity

                    activity.runOnUiThread(Runnable {
                        view.background = image
                    })
                }
            }.start()

        }

        @JvmStatic
        @BindingAdapter("youtubeVideoThumbnail")
        fun loadVideoThumbnail(view: ImageView, videoId: String?) {
            if (!videoId.isNullOrEmpty()) {
//        Glide.with(view.context).load("https://s3.amazonaws.com/appsdeveloperblog/Micky.jpg").into(view)
                var url =
                    GET_YOUTUBE_VIDEO_THUMBNAIL_URL + videoId + YOUTUBE_VIDEO_THUMBNAIL_IMAGE_NAME
                Glide.with(view.context)
                    .load(url)
                    .centerCrop()
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("drawable")
        fun drawable(view: ImageView, drawable: Drawable?) {
            if (drawable != null) {
                Glide.with(view.context)
                    .load(drawable)
                    .centerCrop()
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("selectDate")
        fun dateClicks(editText: EditText, mutableLiveData: MutableLiveData<String>) {
            editText.setOnClickListener {
                selectDate(editText.context,mutableLiveData)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["startTime"], requireAll = true)
        fun startTimeClicks(editText: EditText, startTime: MutableLiveData<String>) {
            editText.setOnClickListener {
                selectTime(editText.context,startTime)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["startTime", "endTime"], requireAll = true)
        fun timeClicks(editText: EditText, startTime: MutableLiveData<String>, endTime: MutableLiveData<String>) {
//            startTime.value = ""
//            endTime.value = ""
            editText.setOnClickListener {
                selectTime(editText.context,startTime,endTime)
            }
        }

//        @JvmStatic
//        @BindingAdapter("touchListener")
//        fun setTouchListener(self : View, value : Boolean) {
//            self.setOnTouchListener(object : View.OnTouchListener{
//                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                    val RIGHT = 2
//                    if (event!!.action == MotionEvent.ACTION_UP){
//                        if (event.rawX >= (self.right - self.drawableState[RIGHT]..width()))
//                    }
//                }
//            })
//        }
    }
}