package com.example.crispminds.master_controller.source

import androidx.annotation.NonNull
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

abstract class MasterDataSource {

    /**
     * Save the category data from the service
     * @param categories
     * @param callback
     */
   open fun saveCategoryMasterDataLocal(
        @NonNull categories: List<CategoryDTO>,
        @NonNull callback: IDataSourceCallback<List<CategoryDTO>>
    ) {
    }

    /**
     * Get the list of category from remote server
     * @param callback
     * @return
     */
    open fun fetchCategoryMasterDataRemote(@NonNull callback: IDataSourceCallback<List<CategoryDTO>>) {}

    /**
     * Get the list of category from local data source
     * @return
     */
    open fun fetchCategoryMasterDataLocal(callback: IDataSourceCallback<List<CategoryDTO>?>) {}

    open fun fetchCategoryMasterDataByDescription(tag:String, callback: IDataSourceCallback<List<CategoryDTO>?>) {}
    open fun saveEmotionMasterDataLocal(
        @NonNull emotions: List<EmotionMasterDTO>,
        @NonNull callback: IDataSourceCallback<List<EmotionMasterDTO>>
    ) {
    }

    open fun fetchEmotionMastersRemote(callback: IDataSourceCallback<List<EmotionMasterDTO>>) {}
    open fun fetchEmotionMastersLocal(callback: IDataSourceCallback<List<EmotionMasterDTO>?>) {}

}