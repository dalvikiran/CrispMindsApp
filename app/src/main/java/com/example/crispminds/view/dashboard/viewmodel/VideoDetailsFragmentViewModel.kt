package com.example.crispminds.view.dashboard.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.categorydtos.VideoDataDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.view.dashboard.remote.DashboardRepository
import com.example.opposfeapp.utils.GenericBaseObservable

class VideoDetailsFragmentViewModel : GenericBaseObservable {

    private var mApplication: Application
    var mDashboardRepository: DashboardRepository
    lateinit var videoData: VideoDataDTO
    lateinit var videoMasterDTO: VideoMasterDTO
    val showProgressBar = MutableLiveData<Boolean>()

    var sharingOptionsVisibility = ObservableBoolean(false)

    private var videoId: Long = 0

    constructor(
        application: Application,
        owner: LifecycleOwner?,
        view: View?,
        dashboardRepository: DashboardRepository
    ) : super(
        application,
        owner,
        view
    ) {
        mApplication = application
        mDashboardRepository = dashboardRepository
    }

    fun onRewardClick() {
        sharingOptionsVisibility.set(!sharingOptionsVisibility.get())
    }


    fun fetchVideoDetailsByVideoId() {

        showProgressBar.value = true

        mDashboardRepository.fetchVideoDetailsByVideoId(
            videoId, object : IDataSourceCallback<VideoMasterDTO> {


                override fun onDataFound(data: VideoMasterDTO) {

                    Log.e("VideoDetails Result", data.toString())
                    showProgressBar.value = false
                }

                override fun onError(error: String?) {
                    showProgressBar.value = false
                    Log.e("Error", error)
                }

            })


    }


}