package com.example.crispminds.view.dashboard.remote

import android.app.Application
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

class DashboardRepository(private var mUserDataSource: DashboardDataSource?) :
    DashboardDataSource {


    companion object {
        @Volatile
        private var INSTANCE: DashboardRepository? = null


        @JvmStatic
        fun getInstance(
            mApplication: Application,
            initRemoteRepository: Boolean
        ): DashboardRepository? {
            if (INSTANCE == null) {
                synchronized(DashboardRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DashboardRepository(
                            if (initRemoteRepository) DashboardRemoteDataSource.getInstance(
                                mApplication
                            ) else null
                        )
                    }
                }
            }
            return INSTANCE
        }
    }

    override fun getCategoryDetails(callback: IDataSourceCallback<List<CategoryDTO>>) {
        mUserDataSource?.getCategoryDetails(callback)
    }


    override fun fetchVideosListByCategoryId(
        categoryId: Long,
        start: Int,
        length: Int,
        search: String,
        callback: IDataSourceCallback<ArrayList<VideoMasterDTO>>
    ) {
        mUserDataSource?.fetchVideosListByCategoryId(
            categoryId,
            start,
            length,
            search,
            callback
        )
    }

    override fun fetchVideoDetailsByVideoId(
        videoId: Long,
        callback: IDataSourceCallback<VideoMasterDTO>
    ) {
        mUserDataSource?.fetchVideoDetailsByVideoId(videoId, callback)
    }
}