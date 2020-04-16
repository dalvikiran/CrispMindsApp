package com.example.crispminds.view.dashboard.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.R
import com.example.crispminds.models.categorydtos.CategoryDTO_old
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.categorydtos.journal.AddNewJournalDTO
import com.example.crispminds.models.categorydtos.journal.JournalDataDTO
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.models.categorydtos.journal.SaveOrUpdateJournalDetails
import com.example.crispminds.retrofit_services.DashboardApi
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import com.example.crispminds.view.dashboard.remote.DashboardRepository
import com.example.opposfeapp.utils.GenericBaseObservable
import kotlinx.coroutines.launch


class JournalFragmentViewModel : GenericBaseObservable {

    private var videoCategoryId: Long? = 0L
    var journalDetailsTitleString = MutableLiveData<String>("New Journal Entry")
    var categoryMutableLiveDataArrayList = MutableLiveData<List<CategoryDTO_old>>()
    var mainJournalContainerDataArrayList = ArrayList<JournalMainCategoryDTO>()
    var journalMutableLiveDataList = MutableLiveData<List<JournalMainCategoryDTO>>()
    var noDataFoundTextVisibility = ObservableBoolean(false)
    private var sharedPreferences: SharedPreferences
    var journalCategoryId: Long? = null
    var mDashboardRepository: DashboardRepository
    var listOfDates = mutableSetOf<String>()
    var listOfDatesSet = MutableLiveData<List<String>>()
    var dateListLayoutVisible = ObservableBoolean(false)
    var searchLayoutVisible = ObservableBoolean(false)
    var journalHappyImage = MutableLiveData<Drawable>()
    var journalConfusedImage = MutableLiveData<Drawable>()
    var journalSadImage = MutableLiveData<Drawable>()
    var journalAngryImage = MutableLiveData<Drawable>()
    private var mApplication: Application
    var journalSmileId: Long? = 0L
    var journalSmileName: String? = ""
    var journalSmileTempId: Long? = 0L
    var journalSelectedDate: String? = ""

    // Journal Entry Section
    val videoSubCategoryArrayList = MutableLiveData<List<CategoryDTO>>()
    var selectedCategoryId = MutableLiveData<Long>()
    var journalDataDTO = MutableLiveData<JournalDataDTO>()
    var isEdit: Boolean = false
    var isEditId: Long = 0L
    var date = MutableLiveData<String>("")
    var combinedTime = MutableLiveData<String>("")
    var startTime = MutableLiveData<String>("")
    var endTime = MutableLiveData<String>("")

    var journalHeadingString = MutableLiveData<String>()
    var journalDescriptionString = MutableLiveData<String>()
    var journalDetailsUpdated = MutableLiveData<Boolean>(false)
    var showProgressBar = MutableLiveData<Boolean>(false)
    var journalSearchQueryString = MutableLiveData<String>("")

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

        journalCategoryId =
            Constants.decrypt(sharedPreferences.getString(Constants.JOURNAL_CATEGORY_ID, "0"))
                ?.toLong()

        populateSmilesData()

    }


    private fun populateSmilesData() {

        showProgressBar.value = true
        coroutineScope.launch {

            try {

                val request = journalCategoryId?.let {
                    DashboardApi.retrofitService.getCategoriesList(
                        it
                    )
                }

                categoryMutableLiveDataArrayList.value = request?.await()

                val responseCategoryArrayList = categoryMutableLiveDataArrayList.value
                if (responseCategoryArrayList != null) {
                    for (item in responseCategoryArrayList) {
                        if (item.name.toLowerCase() == Constants.HAPPY_STRING) {
                            journalSmileId = item.id
                            break
                        }
                    }
                }
                showProgressBar.value = false
                onHappyLayoutClick()
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("Error", e.toString())
            }
        }
    }

    private fun getSelectedCategoryData() {

        showProgressBar.value = true
        coroutineScope.launch {

            try {
                val request = journalSmileId?.let {
                    DashboardApi.retrofitService.getJournalDataList(
                        it
                    )
                }
                val response = request?.await()
                mainJournalContainerDataArrayList = response as ArrayList<JournalMainCategoryDTO>
                journalMutableLiveDataList.value = mainJournalContainerDataArrayList
                val datesList = journalMutableLiveDataList.value

                if (datesList != null) {
                    listOfDates.clear()
                    for (date in datesList) {
                        listOfDates.add(Constants.getFormattedMonthNYear(date.acf?.journal_date)!!)
                    }
                    listOfDatesSet.value = listOfDates.toList()
                    journalSelectedDate = listOfDatesSet.value?.get(0)
                    Constants.getFormattedMonthNYear(
                        mainJournalContainerDataArrayList[0]
                            .acf?.journal_date
                    )?.let { populateDataByDate(it) }
                }

//                Log.e("listOfDates", listOfDates.toString())
                noDataFoundTextVisibility.set(false)
                showProgressBar.value = false
                notifyChange()
            } catch (e: Exception) {
                showProgressBar.value = false
                noDataFoundTextVisibility.set(true)
                Log.e("Error", e.toString())
            }

        }

    }


    fun getVideoSubCategoryData() {

        showProgressBar.value = true
        coroutineScope.launch {

            try {
                videoCategoryId =
                    Constants.decrypt(sharedPreferences.getString(Constants.VIDEO_CATEGORY_ID, ""))
                        ?.toLong()
                val getCategoryList = videoCategoryId?.let {
                    DashboardApi.retrofitService.getImageCategoriesList(
                        it
                    )
                }
                val subCategoryList = getCategoryList?.await()
                videoSubCategoryArrayList.value = subCategoryList
                showProgressBar.value = false
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("SubCategory Error", e.toString())
            }
        }
    }


    private fun setDefaultJournalSmilesImages() {
        journalHappyImage.value = mApplication.resources.getDrawable(R.drawable.ic_happy_yellow)
        journalConfusedImage.value =
            mApplication.resources.getDrawable(R.drawable.ic_confused_yellow)
        journalSadImage.value = mApplication.resources.getDrawable(R.drawable.ic_sad_yellow)
        journalAngryImage.value = mApplication.resources.getDrawable(R.drawable.ic_angry_yellow)
    }

    private fun onClick(smileyString: String) {

        val responseCategoryArrayList = categoryMutableLiveDataArrayList.value
        if (responseCategoryArrayList != null) {
            for (item in responseCategoryArrayList) {
                if (item.name.toLowerCase() == smileyString.toLowerCase()) {
                    journalSmileId = item.id
                    break
                }
            }
        }

        if (journalSmileTempId != journalSmileId) {

            journalSmileTempId = journalSmileId
            journalSmileName = smileyString
            setDefaultJournalSmilesImages()

            when (smileyString) {
                Constants.HAPPY_STRING -> {
                    journalHappyImage.value =
                        mApplication.resources.getDrawable(R.drawable.ic_happy_blue)
                }
                Constants.CONFUSED_STRING -> {
                    journalConfusedImage.value =
                        mApplication.resources.getDrawable(R.drawable.ic_confused_blue)
                }
                Constants.SAD_STRING -> {
                    journalSadImage.value =
                        mApplication.resources.getDrawable(R.drawable.ic_sad_blue)
                }
                Constants.ANGRY_STRING -> {
                    journalAngryImage.value =
                        mApplication.resources.getDrawable(R.drawable.ic_angry_blue)
                }
            }
            notifyChange()
            if (!isEdit) {
                getSelectedCategoryData()
            }
        }
    }

    fun onHappyLayoutClick() {
        onClick(Constants.HAPPY_STRING)
    }

    fun onConfusedLayoutClick() {
        onClick(Constants.CONFUSED_STRING)
    }

    fun onSadLayoutClick() {
        onClick(Constants.SAD_STRING)
    }

    fun onAngryLayoutClick() {
        onClick(Constants.ANGRY_STRING)
    }

    fun onDropdownDateLayoutClick() {
        if (dateListLayoutVisible.get()) {
            dateListLayoutVisible.set(false)
        } else {
            dateListLayoutVisible.set(true)

        }
    }


    fun onSearchClick() {
        onLinearLayoutClick()
        if (searchLayoutVisible.get()) {
            searchLayoutVisible.set(false)
        } else {
            searchLayoutVisible.set(true)
        }
    }


    fun onLinearLayoutClick() {
        if (dateListLayoutVisible.get()) {
            dateListLayoutVisible.set(false)
        }
    }

    fun populateDataByDate(journalDateItemString: String) {

        try {
            onLinearLayoutClick()
            val journalDataList = ArrayList<JournalMainCategoryDTO>()
            journalMutableLiveDataList.value = ArrayList()

            for (data in mainJournalContainerDataArrayList) {
                val formattedDate = Constants.getFormattedMonthNYear(data.acf?.journal_date)
                if (formattedDate.equals(journalDateItemString)) {
                    journalDataList.add(data)
                }
            }

            journalMutableLiveDataList.value = journalDataList

            //                Log.e("listOfDates", listOfDates.toString())
            showProgressBar.value = false
            notifyChange()
        } catch (e: Exception) {
            showProgressBar.value = false
            Log.e("Error", e.toString())
        }


    }


    // Journal Entry Section
    fun populateJournalEntryData(journalMainCategoryDTO: JournalMainCategoryDTO?) {
        if (journalMainCategoryDTO != null) {
            isEdit = true
            journalDetailsTitleString.value = "Edit Journal Details"
        }

        isEditId = journalMainCategoryDTO?.id ?: 0L
        val journalDataDTO = journalMainCategoryDTO?.acf


        if (journalDataDTO?.journal_smiley_name.isNullOrEmpty()) {
            journalDataDTO?.journal_smiley_name?.let { onClick(it) }
        }
        this.journalDataDTO.value = journalDataDTO
        if (journalDataDTO?.journal_date != null) {
            date.value = Constants.getDatePickerFormattedDate2(journalDataDTO.journal_date)
        }

        combinedTime.value = journalDataDTO?.journal_time ?: ""
        if (combinedTime.value!!.contains("-")) {
            startTime.value = combinedTime.value!!.split("-")[0]
            endTime.value = combinedTime.value!!.split("-")[1]
        }
        journalHeadingString.value = journalDataDTO?.journal_heading ?: ""
        journalDescriptionString.value = journalDataDTO?.journal_details ?: ""
        notifyChange()
    }

    fun onDateClick(): String {
        notifyChange()
        return date.value.toString()
    }

    /*  fun onDateClick(): String {
          notifyChange()
          return date.value.toString()
      }*/

    fun time(): String {
        notifyChange()
        combinedTime.value = startTime.value.plus(" - ").plus(endTime.value)
        return combinedTime.value.toString()
    }

    fun onSaveEntryClick() {

        if (validations()) {

            showProgressBar.value = true
            if (isEdit) {

                editOrUpdateJournalDetails(isEditId)

            } else {

                val categories = ArrayList<Int>()
                journalCategoryId?.toInt()?.let { categories.add(it) }
                journalSmileId?.toInt()?.let { categories.add(it) }
                selectedCategoryId.value?.toInt()?.let { categories.add(it) }

                val addNewJournalDTO =
                    AddNewJournalDTO("My test demo with journal", "publish", categories)

                /* val body: RequestBody =
                     RequestBody.create(MediaType.parse("application/json"), addNewJournalDTO.toString())*/
                coroutineScope.launch {

                    val addNewJournalDataResponse =
                        DashboardApi.retrofitService.addNewJournalData(
                            Constants.CONTENT_TYPE,
                            addNewJournalDTO
                        )

                    try {
                        val newJournalData = addNewJournalDataResponse.await().id
                        editOrUpdateJournalDetails(newJournalData)
                    } catch (e: Exception) {
                        showProgressBar.value = false
                        Log.e("Add New Journal Error", e.toString())
                    }
                }

            }


        } else {
            getmSnackbar().value = "Error - Please fill all valid data"
        }

    }

    private fun editOrUpdateJournalDetails(id: Long) {

        if (journalDataDTO.value != null) {

            coroutineScope.launch {

                try {

                    val updateJournalDataResponse =
                        DashboardApi.retrofitService.updateJournalData(
                            Constants.CONTENT_TYPE,
                            id,
                            SaveOrUpdateJournalDetails(journalDataDTO.value!!)
                        )


                    val updateJournalData = updateJournalDataResponse.await()
                    showProgressBar.value = false
                    journalDetailsUpdated.value = true
                    Log.e("Update Journal Data", updateJournalData.toString())
                } catch (e: Exception) {
                    showProgressBar.value = false
                    Log.e("Update Error", e.toString())
                }
            }
        } else {
            showProgressBar.value = false
        }
    }

    private fun validations(): Boolean {

        var isValid = true

        if (date.value.isNullOrEmpty() || startTime.value.isNullOrEmpty() || endTime.value.isNullOrEmpty()
            || selectedCategoryId.value == 0L || journalSmileId == 0L || journalHeadingString.value.isNullOrEmpty()
            || journalDescriptionString.value.isNullOrEmpty()
        ) {
            isValid = false
        } else {

            var date = date.value

            if (date?.contains("-")!!) {
                date = Constants.getDatePickerFormattedDate4(date)
            }
            journalDataDTO.value = JournalDataDTO(
                date,
                combinedTime.value,
                journalHeadingString.value,
                journalDescriptionString.value
            )
        }

        return isValid

    }

    fun onDeleteItem(position: Int, journalItemDetail: JournalMainCategoryDTO) {

        showProgressBar.value = true
        coroutineScope.launch {

            try {
                val getCategoryList = journalItemDetail.id?.let {
                    DashboardApi.retrofitService.deleteJournalData(
                        it
                    )
                }

                val subCategoryList = getCategoryList?.await()
                getmSnackbar().value = "Deleted"
                onHappyLayoutClick()
                mainJournalContainerDataArrayList.remove(mainJournalContainerDataArrayList[position])
                journalMutableLiveDataList.value = mainJournalContainerDataArrayList
                showProgressBar.value = false
            } catch (e: Exception) {
                showProgressBar.value = false
                Log.e("SubCategory Error", e.toString())
            }
        }
    }


    @SuppressLint("DefaultLocale")
    fun searchByText(searchQuery: String?) {

        if (!searchQuery.isNullOrEmpty()) {
            val searchedDataList = ArrayList<JournalMainCategoryDTO>()
            for (item in mainJournalContainerDataArrayList) {
                if ((item.acf?.journal_heading?.toLowerCase()
                        ?.contains(searchQuery.toLowerCase())!!) || (item.acf?.journal_details?.toLowerCase()
                        ?.contains(
                            searchQuery.toLowerCase()
                        )!!)
                ) {
                    searchedDataList.add(item)
                }
            }
            journalMutableLiveDataList.value = searchedDataList
        } else {
            getmSnackbar().value = "Please enter search text"
        }

    }

    fun maintainJournalData() {

        journalMutableLiveDataList.value = mainJournalContainerDataArrayList

    }

}