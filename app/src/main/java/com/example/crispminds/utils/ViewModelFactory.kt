package com.example.crispminds.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(context: Context) : ViewModelProvider.Factory {

    var context: Context = context

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        /* if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
             return LoginViewModel(context) as T
         }*//*else if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(context) as T
        }*/
        /*if (modelClass.isAssignableFrom(VideoFragmentViewModel::class.java)){
            return VideoFragmentViewModel() as T
        }*/

        throw IllegalStateException("Unknown view model class")
    }

}