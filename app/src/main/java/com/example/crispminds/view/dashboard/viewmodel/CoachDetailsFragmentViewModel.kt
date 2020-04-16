package com.example.crispminds.view.dashboard.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crispminds.models.CoachDTO
import com.example.crispminds.models.CoachDetailResponse
import com.example.crispminds.models.CoachListResponse
import com.example.crispminds.models.Testimonial
import com.example.crispminds.retrofit_services.DashboardApi
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import kotlinx.coroutines.launch

class CoachDetailsFragmentViewModel(context: Context, coachDetailDTO: CoachDTO) :
    ViewModel() {

    lateinit var context: Context
    lateinit var coachDetailDTO: CoachDTO
    lateinit var mSharedPreferences: SharedPreferences

    val showProgressBar = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<String>()

    private lateinit var coachesDetailResponse: CoachDetailResponse
    var coachDto = MutableLiveData<CoachDTO>()

//    var testimonial = MutableLiveData<String>()

    init {
        this.context = context
        this.coachDetailDTO = coachDetailDTO
        mSharedPreferences = Constants.getSharedPreferences(context)
        var coachesCategoryId: Long? =
            Constants.decrypt(mSharedPreferences.getString(Constants.COACHES_CATEGORY_ID, ""))
                ?.toLong()

//        testimonial.value = coachDetailDTO.acf.testimonials.get(0).testimonials
        Log.i("CoachDetFragViewModel", "CoachDetailsFragmentViewModel init")
    }

    fun getCoachDetails(coachId : Long){
        showProgressBar.value = true
        coroutineScope.launch {

            var getCoachesDetails = DashboardApi.retrofitService.getCoacheDetails(coachId)
//            var getCoachesList = DashboardApi.retrofitService.getCoachesList("desc", "coachId",true)
            try {
                coachesDetailResponse = getCoachesDetails.await()
                if (coachesDetailResponse != null){
                    if (coachesDetailResponse.success != null){
                        coachDto.value = coachesDetailResponse.coach!!
                    }else if (coachesDetailResponse.error != null){
//                        Log.e("Error", coachesListResponse.error.message)
                        showMessage.value = coachesDetailResponse.error!!.message
                    }
                }
//
                showProgressBar.value = false
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("Error", e.toString())
                showMessage.value = e.toString()
            }
        }
    }


}