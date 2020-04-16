package com.example.crispminds.view.dashboard.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.crispminds.models.CategoryMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.source.CrispMindRoomDatabase
import com.example.crispminds.source.sub_category.CategoryRepository
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.dashboard.remote.DashboardRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch

class DashboardViewModel : GenericBaseObservable {

    var mDashboardRepository: DashboardRepository

    private val categoryRepository: CategoryRepository
//    val subCategoriesList = MutableLiveData<List<CategoryDTO>>()

    var sharedPreferences: SharedPreferences
    var application: Application


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

        this.application = application
        sharedPreferences = Constants.getSharedPreferences(application.applicationContext)
        mDashboardRepository = dashboardRepository

        val subCategoryDao = CrispMindRoomDatabase.getDatabase(application).subCategoryDao()
        categoryRepository = CategoryRepository(subCategoryDao)
    }

    fun populateData() {


        coroutineScope.launch {

            /*val getCategoryList = DashboardApi.retrofitService.getCategoriesList(0)
            try {
                val categoryList = getCategoryList.await()

                for (item in categoryList) {

                    when (item.name) {
                        Constants.VIDEO_CATEGORY_STRING -> {

                            sharedPreferences.edit().putString(
                                Constants.VIDEO_CATEGORY_ID, Constants.encrypt(
                                    item.id.toString()
                                )
                            ).apply()

                        }

                        Constants.COACHES_CATEGORY_STRING -> {

                            sharedPreferences.edit().putString(
                                Constants.COACHES_CATEGORY_ID, Constants.encrypt(
                                    item.id.toString()
                                )
                            ).apply()
                        }

                        Constants.JOURNAL_CATEGORY_STRING -> {

                            sharedPreferences.edit().putString(
                                Constants.JOURNAL_CATEGORY_ID, Constants.encrypt(
                                    item.id.toString()
                                )
                            ).apply()
                        }
                    }
                }


                Log.e("categoryList", categoryList.toString())

            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }


            val getSubCategoriesList = DashboardApi.retrofitService.getAllSubCategoriesList()
            try {
                val subCategoryList = getSubCategoriesList.await()
                if (subCategoryList.size > 0){
//                    subCategoriesList.value = subCategoryList
                    inserSubCategoryData(subCategoryList)
                }
            }catch (e: Exception){
                Log.e("Sub Category Error", e.toString())
            }*/

            mDashboardRepository.getCategoryDetails(object : IDataSourceCallback<List<CategoryDTO>> {
                override fun onDataFound(data: List<CategoryDTO>) {
                    inserSubCategoryData(data)
                }

                override fun onDataNotFound() {

                }
                override fun onError(error: String?) {

                }
            })


        }
    }

    fun inserSubCategoryData(categories: List<CategoryDTO>) {

        coroutineScope.launch {

            categoryRepository.insertData(categories)
        }
    }


}