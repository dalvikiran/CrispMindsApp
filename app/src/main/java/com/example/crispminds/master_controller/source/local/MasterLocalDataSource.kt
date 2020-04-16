package com.example.crispminds.master_controller.source.local;

import androidx.annotation.NonNull
import com.example.crispminds.master_controller.source.MasterDataSource
import com.example.crispminds.master_controller.source.daos.EmotionMasterDao
import com.example.crispminds.master_controller.sync.AppExecutors
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.source.sub_category.CategoryDao

class MasterLocalDataSource : MasterDataSource {

    companion object {

        @Volatile
        private var INSTANCE: MasterLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            appExecutors: AppExecutors,
            categoryDao: CategoryDao,
            emotionMasterDao: EmotionMasterDao

        ): MasterLocalDataSource? {
            if (INSTANCE == null) {
                synchronized(
                    MasterLocalDataSource::class.java
                ) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            MasterLocalDataSource(
                                appExecutors,
                                categoryDao,
                                emotionMasterDao
                            )
                    }
                }
            }
            return INSTANCE
        }


    }

    private var mAppExecutors: AppExecutors? = null
    private var categoryDao: CategoryDao? = null
    private var emotionMasterDao: EmotionMasterDao? = null


    constructor(
        mAppExecutors: AppExecutors, categoryDao: CategoryDao, emotionMasterDao: EmotionMasterDao
    ) {
        this.mAppExecutors = mAppExecutors
        this.categoryDao = categoryDao
        this.emotionMasterDao = emotionMasterDao
    }


    override fun saveCategoryMasterDataLocal(
        categories: List<CategoryDTO>,
        callback: IDataSourceCallback<List<CategoryDTO>>
    ) {

        val completeRunnable = Runnable {
            try {
//                categoryDao?.deleteAllSubcategory()
                categoryDao?.insert(categories)
                mAppExecutors?.mainThread()?.execute { callback.onDataFound(categories) }
            } catch (ex: Exception) {
                mAppExecutors?.mainThread()?.execute { callback.onError(ex.localizedMessage) }
            }
        }
        mAppExecutors?.diskIO()?.execute(completeRunnable)

    }


    override fun fetchCategoryMasterDataLocal(callback: IDataSourceCallback<List<CategoryDTO>?>) {

        val completeRunnable = Runnable {
            try {
                val categoryList: List<CategoryDTO>? = categoryDao?.getSubcategory()
                mAppExecutors?.mainThread()?.execute { callback.onDataFound(categoryList) }
            } catch (ex: Exception) {
                mAppExecutors?.mainThread()?.execute { callback.onError(ex.localizedMessage) }
            }
        }
        mAppExecutors?.diskIO()?.execute(completeRunnable)
    }


    override fun fetchCategoryMasterDataByDescription(
        tag: String,
        callback: IDataSourceCallback<List<CategoryDTO>?>
    ) {

        val completeRunnable = Runnable {
            try {
                val categoryList: List<CategoryDTO>? = categoryDao?.getSubcategoryByDescription(tag)
                mAppExecutors?.mainThread()?.execute { callback.onDataFound(categoryList) }
            } catch (ex: Exception) {
                mAppExecutors?.mainThread()?.execute { callback.onError(ex.localizedMessage) }
            }
        }
        mAppExecutors?.diskIO()?.execute(completeRunnable)
    }


    override fun saveEmotionMasterDataLocal(
        @NonNull emotions: List<EmotionMasterDTO>,
        @NonNull callback: IDataSourceCallback<List<EmotionMasterDTO>>
    ) {
        val completeRunnable = Runnable {
            try {
                emotionMasterDao?.deleteAllEmotions()
                emotionMasterDao?.insert(emotions)
                mAppExecutors?.mainThread()?.execute { callback.onDataFound(emotions) }
            } catch (ex: Exception) {
                mAppExecutors?.mainThread()?.execute { callback.onError(ex.localizedMessage) }
            }
        }
        mAppExecutors?.diskIO()?.execute(completeRunnable)

    }

    override fun fetchEmotionMastersLocal(callback: IDataSourceCallback<List<EmotionMasterDTO>?>) {

        val completeRunnable = Runnable {
            try {
                val emotionsList: List<EmotionMasterDTO>? = emotionMasterDao?.getAllEmotions()
                mAppExecutors?.mainThread()?.execute { callback.onDataFound(emotionsList) }
            } catch (ex: Exception) {
                mAppExecutors?.mainThread()?.execute { callback.onError(ex.localizedMessage) }
            }
        }
        mAppExecutors?.diskIO()?.execute(completeRunnable)
    }

}
