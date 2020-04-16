package com.example.crispminds.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import com.example.crispminds.view.dashboard.remote.DashboardRepository
import com.example.crispminds.view.dashboard.viewmodel.*
import com.example.crispminds.view.mysession.viewmodel.MySessionViewModel
import com.example.crispminds.view.userlogin.remote.UserRepository
import com.example.crispminds.viewmodels.ForgotPasswordViewModel
import com.example.crispminds.viewmodels.LoginViewModel
import com.example.crispminds.viewmodels.RegistrationViewModel
import com.example.opposfeapp.utils.GenericBaseObservable

class Utils {
    companion object {

        fun <T : GenericBaseObservable?> obtainBaseObservable(
            activity: AppCompatActivity,
            modelClass: Class<T>,
            owner: LifecycleOwner?,
            view: View?
        ): T? {

            return when {

                // Login ViewModel
                modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                    LoginViewModel(
                        activity.application, owner, view,
                        UserRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Registration ViewModel
                modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> {
                    RegistrationViewModel(
                        activity.application, owner, view,
                        UserRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Forgot Password ViewModel
                modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> {
                    ForgotPasswordViewModel(
                        activity.application, owner, view,
                        UserRepository.getInstance(activity.application, true)!!
                    ) as T
                }


                // Dashboard ViewModel
                modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                    DashboardViewModel(
                        activity.application, owner, view,
                        DashboardRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Video ViewModel
                modelClass.isAssignableFrom(VideoFragmentViewModel::class.java) -> {
                    VideoFragmentViewModel(
                        activity.application, owner, view,
                        DashboardRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Video details ViewModel
                modelClass.isAssignableFrom(VideoDetailsFragmentViewModel::class.java) -> {
                    VideoDetailsFragmentViewModel(
                        activity.application, owner, view,
                        DashboardRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Coaches ViewModel
                modelClass.isAssignableFrom(CoachesFragmentViewModel::class.java) -> {
                    CoachesFragmentViewModel(
                        activity.application, owner, view
                    ) as T
                }

                // Journal ViewModel
                modelClass.isAssignableFrom(JournalFragmentViewModel::class.java) -> {
                    JournalFragmentViewModel(
                        activity.application, owner, view,
                        DashboardRepository.getInstance(activity.application, true)!!
                    ) as T
                }

                // Profile ViewModel
                modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java) -> {
                    ProfileFragmentViewModel(
                        activity.application, owner, view
                    ) as T
                }

                // MySession ViewModel
                modelClass.isAssignableFrom(MySessionViewModel::class.java) -> {
                    MySessionViewModel(
                        activity.application, owner, view
                    ) as T
                }
                else -> null
            }

        }

        fun getSpannableText(
            context: Context,
            textView: TextView,
            spannableText: Int,
            color: Int, start: Int, end: Int
        ) {
            val wordToSpan: Spannable =
                SpannableString(context.resources.getString(spannableText).toString())

            wordToSpan.setSpan(
                ForegroundColorSpan(context.resources.getColor(color)),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = wordToSpan
        }


        fun twoWordsSpannableText(
            context: Context,
            textView: TextView,
            spannableText: Int,
            color1: Int, start1: Int, end1: Int,
            color2: Int, start2: Int, end2: Int
        ) {
            val wordToSpan: Spannable =
                SpannableString(context.resources.getString(spannableText).toString())

            wordToSpan.setSpan(
                ForegroundColorSpan(context.resources.getColor(color1)),
                start1,
                end1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            wordToSpan.setSpan(
                ForegroundColorSpan(context.resources.getColor(color2)),
                start2,
                end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = wordToSpan
        }


        @BindingAdapter(value = ["errorText", "context"], requireAll = false)
        @JvmStatic
        fun setErrorMessage(
            view: TextView,
            errorMessage: String,
            context: Context
        ) {
            view.text = "Error - $errorMessage"
        }


    }


}