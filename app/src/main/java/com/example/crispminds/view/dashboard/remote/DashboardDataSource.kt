package com.example.crispminds.view.dashboard.remote

import androidx.annotation.NonNull
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

interface DashboardDataSource {

    fun getCategoryDetails(@NonNull callback: IDataSourceCallback<List<CategoryDTO>>)

    fun fetchVideosListByCategoryId(
        @NonNull categoryId: Long,
        start: Int,
        length: Int,
        search: String,
        @NonNull callback: IDataSourceCallback<ArrayList<VideoMasterDTO>>
    )

    fun fetchVideoDetailsByVideoId(
        @NonNull videoId: Long,
        @NonNull callback: IDataSourceCallback<VideoMasterDTO>
    )


}