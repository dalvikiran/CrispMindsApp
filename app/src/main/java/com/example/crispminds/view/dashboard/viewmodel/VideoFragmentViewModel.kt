package com.example.crispminds.view.dashboard.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.videosdto.VideoMasterDTO
import com.example.crispminds.retrofit_services.DashboardApi
import com.example.crispminds.retrofit_services.NetworkController
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.source.CrispMindRoomDatabase
import com.example.crispminds.source.sub_category.CategoryRepository
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.dashboard.remote.DashboardRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch

class VideoFragmentViewModel : GenericBaseObservable {

    var originalVideoList = ArrayList<VideoMasterDTO>()
    private var mApplication: Application
    var mDashboardRepository: DashboardRepository
    private var sharedPreferences: SharedPreferences

    private val categoryRepository: CategoryRepository

    val videoSubCategoryArrayList = MutableLiveData<List<CategoryDTO>>()
    val updatedVideoArrayList = MutableLiveData<List<VideoMasterDTO>>()
    var searchLayoutVisible = ObservableBoolean(false)
    var searchQueryString = MutableLiveData<String>("")
    val showProgressBar = MutableLiveData<Boolean>()
    var count = ObservableField<Int>(0)


    var start: Int = 0
    var length: Int = 10
    var search: String = ""

    lateinit var networkController: NetworkController

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

        sharedPreferences = Constants.getSharedPreferences(application.applicationContext)

        val subCategoryDao = CrispMindRoomDatabase.getDatabase(application).subCategoryDao()
        categoryRepository = CategoryRepository(subCategoryDao)
    }


    fun onSearchClick() {
        if (searchLayoutVisible.get()) {
            searchLayoutVisible.set(false)
        } else {
            searchLayoutVisible.set(true)
        }
    }


    fun getVideoSubCategoryData(categoryId: Long) {

//        var videoSubCateList : MutableLiveData<List<CategoryDTO>>
        coroutineScope.launch {

            videoSubCategoryArrayList.value = categoryRepository.getCategorie(Constants.ALL)

            getVideoData(categoryId, videoSubCategoryArrayList.value!!.get(0).categoryId)
        }


        /* showProgressBar.value = true
         coroutineScope.launch {

             var getCategoryList = DashboardApi.retrofitService.getImageCategoriesList(categoryId)
             try {
                 var subCategoryList = getCategoryList.await()
                 videoSubCategoryArrayList.value = subCategoryList
                 getVideoData(categoryId)
             } catch (e: Exception) {
                 showProgressBar.value = false
                 Log.e("SubCategory Error", e.toString())
             }
         }*/
    }

    fun getVideoData(categoryId: Long, subCategoryId: Long) {
        showProgressBar.value = true
        coroutineScope.launch {

            // Get Video data
            var getVideoList =
                DashboardApi.retrofitService.getVideosDataList(categoryId!!, subCategoryId!!)

            try {
                originalVideoList = getVideoList?.await() as ArrayList<VideoMasterDTO>
                populateData(originalVideoList)
                showProgressBar.value = false
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("Error", e.toString())
            }
        }

    }


    fun searchByText(searchQuery: String?) {

        if (!searchQuery.isNullOrEmpty()) {
            val searchedDataList = ArrayList<VideoMasterDTO>()
            for (item in originalVideoList) {
                if ((item.videoTitle?.toLowerCase()
                        ?.contains(searchQuery.toLowerCase())!!) || (item.videoDesc?.toLowerCase()
                        ?.contains(
                            searchQuery.toLowerCase()
                        )!!)
                ) {
                    searchedDataList.add(item)
                }
            }
            populateData(searchedDataList)
        } else {
            getmSnackbar().value = "Please enter search text"
        }

    }

    fun populateData(arrayList: ArrayList<VideoMasterDTO>) {
        count.set(arrayList.size)
        updatedVideoArrayList.value = arrayList
    }


    fun fetchVideosListByCategoryId(categoryId: Long) {

        showProgressBar.value = true
        mDashboardRepository.fetchVideosListByCategoryId(
            categoryId,
            start,
            length,
            search,
            object : IDataSourceCallback<ArrayList<VideoMasterDTO>> {


                override fun onDataFound(data: ArrayList<VideoMasterDTO>) {

                    Log.e("Video List Result", data.toString())
                    originalVideoList = data
                    populateData(originalVideoList)
                    showProgressBar.value = false
                }

                override fun onError(error: String?) {
                    showProgressBar.value = false
                    Log.e("Error", error)
                }

            })


    }


}