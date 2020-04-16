package com.example.crispminds.view.dashboard.remote

import android.content.Context
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.modeldtos.EmotionsResponseDTO
import com.example.crispminds.models.CategoryResponseDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.retrofit_services.NetworkController
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.NetworkResponseCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.CustomGsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONObject

class DashboardRemoteDataSource : DashboardDataSource {

    companion object {
        private var instance: DashboardRemoteDataSource? = null
        private var networkController: NetworkController? = null
        private var mContext: Context? = null

        @JvmStatic
        fun getInstance(context: Context?): DashboardDataSource? {
            mContext = context
            networkController = NetworkController.getInstance(context!!)
            if (instance == null) {
                instance = DashboardRemoteDataSource()
            }
            return instance
        }
    }

    // Category Master
    override fun getCategoryDetails(callback: IDataSourceCallback<List<CategoryDTO>>) {
        coroutineScope.launch {
            networkController!!.getCategoryDetails(object : NetworkResponseCallback {
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


    /* Video Section */

    override fun fetchVideoDetailsByVideoId(
        videoId: Long,
        callback: IDataSourceCallback<VideoMasterDTO>
    ) {

        coroutineScope.launch {

            networkController?.fetchVideoDetails(videoId, object : NetworkResponseCallback {
                override fun onSuccess(data: String) {

                    try {

                        if (!data.isNullOrBlank()) {

                            val jsonObject = JSONObject(data)
                            if (jsonObject.has("videoMaster")) {

                                val gson = CustomGsonBuilder().getInstance().create()
                                val videoMasterDTO: VideoMasterDTO = gson.fromJson(
                                    jsonObject.getJSONObject("videoMaster").toString(),
                                    VideoMasterDTO::class.java
                                )

                                if (videoMasterDTO != null) {
                                    callback.onDataFound(videoMasterDTO)
                                } else {
                                    callback.onDataNotFound()
                                }

                            } else if (jsonObject.has("error")) {
                                val errorJSONObject = jsonObject.getJSONObject("error")
                                callback.onError(errorJSONObject.getString("message"))
                            } else {
                                callback.onError(NetworkController.SERVER_ERROR)
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onError(errorCode: Int, errorData: String) {
                    callback.onError(errorData)
                }

            })
        }

    }

    override fun fetchVideosListByCategoryId(
        categoryId: Long,
        start: Int,
        length: Int,
        search: String, callback: IDataSourceCallback<ArrayList<VideoMasterDTO>>
    ) {

        coroutineScope.launch {

            networkController?.fetchVideosListByCategoryId(
                categoryId, start, length, search,
                object : NetworkResponseCallback {
                    override fun onSuccess(data: String) {

                        try {

                            if (!data.isNullOrBlank()) {
                                val jsonObject = JSONObject(data)

                                if (jsonObject.has("VideoList")) {

                                    val jsonArray = jsonObject.getJSONArray("VideoList")

                                    if (jsonArray != null && jsonArray.length() > 0) {
                                        val gson = CustomGsonBuilder().getInstance().create()
                                        var videoArrayList =
                                            gson.fromJson<ArrayList<VideoMasterDTO>>(
                                                jsonArray.toString(),
                                                object :
                                                    TypeToken<ArrayList<VideoMasterDTO?>?>() {}.type
                                            )

                                        if (videoArrayList != null && videoArrayList.size > 0) {
                                            callback.onDataFound(videoArrayList)
                                        } else {
                                            callback.onDataNotFound()
                                        }

                                    } else {
                                        callback.onDataNotFound()
                                    }

                                } else if (jsonObject.has("error")) {

                                    val errorJSONObject = jsonObject.getJSONObject("error")
                                    callback.onError(errorJSONObject.getString("message"))
                                } else {
                                    callback.onError(NetworkController.SERVER_ERROR)
                                }
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onError(errorCode: Int, errorData: String) {
                        callback.onError(errorData)
                    }

                })
        }

    }


}