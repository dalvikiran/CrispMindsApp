package com.example.crispminds.view.dashboard.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.CoachDTO
import com.example.crispminds.models.CoachListResponse
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.DashboardApi
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.source.CrispMindRoomDatabase
import com.example.crispminds.source.sub_category.CategoryRepository
import com.example.crispminds.utils.Constants
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch

class CoachesFragmentViewModel : GenericBaseObservable {

    private lateinit var coachesListResponse: CoachListResponse
    private lateinit var coachesList: List<CoachDTO>
    private var mApplication: Application
    val coachesSubCategoryArrayList = MutableLiveData<List<CategoryDTO>>()
    val coachesDetailsArrayList = MutableLiveData<List<CoachDTO>>()
    var coachesCount = MutableLiveData<String>("0")
    var searchLayoutVisible = ObservableBoolean(false)
    var searchQueryString = MutableLiveData<String>("")
    var showMessage = MutableLiveData<String>()
    val showProgressBar = MutableLiveData<Boolean>()
    var coachCategoryId : Long? = 0

    private val categoryRepository : CategoryRepository

    constructor(
        application: Application,
        owner: LifecycleOwner?,
        view: View?
    ) : super(
        application,
        owner,
        view
    ) {
        mApplication = application

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

    fun getCoachesSubCategoryData(categoryId: Long) {
        showProgressBar.value = true
        coroutineScope.launch {

            coachesSubCategoryArrayList.value = categoryRepository.getCategorie(Constants.ALL)
            getCoachesData(categoryId, coachesSubCategoryArrayList.value!!.get(0).categoryId)
           /* var getCategoryList = DashboardApi.retrofitService.getImageCategoriesList(categoryId)
            try {
                var subCategoryList = getCategoryList.await()
                coachesSubCategoryArrayList.value = subCategoryList
                getCoachesData(categoryId)
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("SubCategory Error", e.toString())
            }*/
        }
    }

    fun getCoachesData(categoryId: Long, subCategoryId: Long) {



        coroutineScope.launch {

           /* var getCoachesList = DashboardApi.retrofitService.getCoachesList(categoryId, subCategoryId)
            try {
                coachesList = getCoachesList.await()
                populateData()
                showProgressBar.value = false
                notifyChange()
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("Error", e.toString())
            }*/
        }

    }

    fun getCoachesPagination(){
        showProgressBar.value = true
        coroutineScope.launch {

            var getCoachesList = DashboardApi.retrofitService.getCoachesList()
//            var getCoachesList = DashboardApi.retrofitService.getCoachesList("desc", "coachId",true)
            try {
                coachesListResponse = getCoachesList.await()
                if (coachesListResponse != null){
                    if (coachesListResponse.success != null){
                        coachesList = coachesListResponse.CoachList!!
                        populateData()
                        notifyChange()
                    }else if (coachesListResponse.error != null){
//                        Log.e("Error", coachesListResponse.error.message)
                        showMessage.value = coachesListResponse.error!!.message
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

    fun searchByText(searchQuery: String?) {

        if (!searchQuery.isNullOrEmpty()) {
            val searchedDataList = ArrayList<CoachDTO>()
            for (item in coachesList) {
                if ((item.coachName?.toLowerCase()?.contains(searchQuery.toLowerCase())!!) || (item.aboutCoach?.toLowerCase()?.contains(
                        searchQuery.toLowerCase()
                    )!!)
                ) {
                    searchedDataList.add(item)
                }
            }
            coachesDetailsArrayList.value = searchedDataList
        } else {
            getmSnackbar().value = "Please enter search text"
        }

    }

    fun populateData() {
        coachesDetailsArrayList.value = coachesList
    }


}