package com.example.crispminds.master_controller.source.remote

import android.content.Context
import com.example.crispminds.master_controller.source.MasterDataSource
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.modeldtos.EmotionsResponseDTO
import com.example.crispminds.models.CategoryResponseDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.NetworkController
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.NetworkResponseCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.CustomGsonBuilder
import kotlinx.coroutines.launch
import org.json.JSONObject

class MasterRemoteDataSource : MasterDataSource() {

    companion object {
        private var instance: MasterRemoteDataSource? = null
        private var networkController: NetworkController? = null
        private var mContext: Context? = null

        @JvmStatic
        fun getInstance(context: Context?): MasterDataSource? {
            mContext = context
            networkController = NetworkController.getInstance(context!!)
            if (instance == null) {
                instance = MasterRemoteDataSource()
            }
            return instance
        }
    }

    // Category Master
    override fun fetchCategoryMasterDataRemote(callback: IDataSourceCallback<List<CategoryDTO>>) {
        coroutineScope.launch {
            networkController?.getCategoryDetails(object : NetworkResponseCallback {
                override fun onSuccess(data: String) {
                    val jsonObject = JSONObject(data)
                    val gson = CustomGsonBuilder().getInstance().create()
                    val categoryResponseDTO: CategoryResponseDTO = gson.fromJson(
                        jsonObject.toString(),
                        CategoryResponseDTO::class.java
                    )
                    if (categoryResponseDTO.success != null) {
                        if (categoryResponseDTO.CategoryMasterList.isNotEmpty())
                            callback.onDataFound(categoryResponseDTO.CategoryMasterList)
                        else
                            callback.onDataNotFound()
                    } else if (categoryResponseDTO.error != null) {
                        callback.onError(categoryResponseDTO.error.message)
                    } else {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }

                override fun onError(errorCode: Int, errorData: String) {
                    callback.onError(errorData)
                }
            })
        }
    }


    // Emotion Master
    override fun fetchEmotionMastersRemote(callback: IDataSourceCallback<List<EmotionMasterDTO>>) {

        coroutineScope.launch {
            networkController?.fetchEmotionMasters(object : NetworkResponseCallback {
                override fun onSuccess(data: String) {
                    val jsonObject = JSONObject(data)
                    val gson = CustomGsonBuilder().getInstance().create()
                    val emotionMasterDTO = gson.fromJson(
                        jsonObject.toString(),
                        EmotionsResponseDTO::class.java
                    )
                    if (emotionMasterDTO.success != null) {
                        if (emotionMasterDTO.EmotionMasterList.isNotEmpty())
                            callback.onDataFound(emotionMasterDTO.EmotionMasterList)
                        else
                            callback.onDataNotFound()
                    } else if (emotionMasterDTO.error != null) {
                        callback.onError(emotionMasterDTO.error.message)
                    } else {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }

                override fun onError(errorCode: Int, errorData: String) {
                    callback.onError(errorData)
                }
            })
        }
    }


}