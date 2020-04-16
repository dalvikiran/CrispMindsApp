package com.example.crispminds.utils.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crispminds.models.CoachDTO
import com.example.crispminds.view.dashboard.viewmodel.BookSessionFragmentViewModel
import com.example.crispminds.view.dashboard.viewmodel.CoachDetailsFragmentViewModel
import java.lang.IllegalStateException

class CoachDetailsViewModelFactory(context: Context, coachDetailDTO: CoachDTO) : ViewModelProvider.Factory {

    var context : Context
    var coachDetailDTO: CoachDTO

    init {
        this.context = context
        this.coachDetailDTO = coachDetailDTO
    }

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CoachDetailsFragmentViewModel::class.java)){
            return CoachDetailsFragmentViewModel(context,coachDetailDTO) as T
        }else if (modelClass.isAssignableFrom(BookSessionFragmentViewModel::class.java)){
            return BookSessionFragmentViewModel(context,coachDetailDTO) as T
        }

        throw IllegalStateException("Unknown view model class")
    }

}