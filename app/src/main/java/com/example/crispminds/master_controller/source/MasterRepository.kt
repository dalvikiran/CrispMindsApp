package com.example.crispminds.master_controller.source

import android.content.Context
import com.example.crispminds.master_controller.source.local.MasterLocalDataSource
import com.example.crispminds.master_controller.source.remote.MasterRemoteDataSource
import com.example.crispminds.master_controller.sync.AppExecutors
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.source.CrispMindRoomDatabase
import com.example.crispminds.view.userlogin.remote.UserRepository

class MasterRepository : MasterDataSource {

    companion object {
        @Volatile
        private var INSTANCE: MasterRepository? = null

        fun getInstance(context: Context?): MasterRepository? {
            if (INSTANCE == null) {
                synchronized(UserRepository::class.java) {
                    if (INSTANCE == null) {
                        val database: CrispMindRoomDatabase =
                            CrispMindRoomDatabase.getDatabase(context!!)
                        INSTANCE = MasterRepository(
                            MasterRemoteDataSource.getInstance(context),
                            MasterLocalDataSource.getInstance(
                                AppExecutors(),
                                database.subCategoryDao(),
                                database.emotionDao()
                            )
                        )
                    }
                }
            }
            return INSTANCE
        }
    }

    private var mMasterRemoteDataSource: MasterDataSource? = null
    private var mMasterLocalDataSource: MasterDataSource? = null

    constructor(
        mMasterRemoteDataSource: MasterDataSource?,
        mMasterLocalDataSource: MasterDataSource?
    ) {
        this.mMasterRemoteDataSource = mMasterRemoteDataSource
        this.mMasterLocalDataSource = mMasterLocalDataSource
    }


    /*--------  Category section -----------*/

    override fun saveCategoryMasterDataLocal(
        categories: List<CategoryDTO>,
        callback: IDataSourceCallback<List<CategoryDTO>>
    ) {
        mMasterLocalDataSource?.saveCategoryMasterDataLocal(categories, callback)
    }

    override fun fetchCategoryMasterDataLocal(callback: IDataSourceCallback<List<CategoryDTO>?>) {
        mMasterLocalDataSource?.fetchCategoryMasterDataLocal(callback)
    }

    override fun fetchCategoryMasterDataRemote(callback: IDataSourceCallback<List<CategoryDTO>>) {

        mMasterRemoteDataSource?.fetchCategoryMasterDataRemote(object :
            IDataSourceCallback<List<CategoryDTO>> {

            override fun onDataFound(data: List<CategoryDTO>) {
                saveCategoryMasterDataLocal(data, callback);
            }

            override fun onError(error: String?) {
                callback.onError(error)
            }

        })

    }

    override fun fetchCategoryMasterDataByDescription(
        tag: String,
        callback: IDataSourceCallback<List<CategoryDTO>?>
    ) {
        mMasterLocalDataSource?.fetchCategoryMasterDataByDescription(tag, callback)
    }


    /*--------  Emotion section -----------*/

    override fun saveEmotionMasterDataLocal(
        emotions: List<EmotionMasterDTO>,
        callback: IDataSourceCallback<List<EmotionMasterDTO>>
    ) {
        mMasterLocalDataSource?.saveEmotionMasterDataLocal(emotions, callback)
    }

    override fun fetchEmotionMastersLocal(callback: IDataSourceCallback<List<EmotionMasterDTO>?>) {
        mMasterLocalDataSource?.fetchEmotionMastersLocal(callback)
    }

    override fun fetchEmotionMastersRemote(callback: IDataSourceCallback<List<EmotionMasterDTO>>) {
        mMasterRemoteDataSource?.fetchEmotionMastersRemote(object :
            IDataSourceCallback<List<EmotionMasterDTO>> {

            override fun onDataFound(data: List<EmotionMasterDTO>) {
                saveEmotionMasterDataLocal(data, callback)
            }

            override fun onError(error: String?) {
                callback.onError(error)
            }

        })
    }
}