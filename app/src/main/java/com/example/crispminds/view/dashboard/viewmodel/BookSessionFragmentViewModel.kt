package com.example.crispminds.view.dashboard.viewmodel

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crispminds.models.*
import com.example.crispminds.retrofit_services.DashboardApi
import com.example.crispminds.retrofit_services.network_utils.coroutineScope
import com.example.crispminds.utils.Constants
import kotlinx.coroutines.launch
import com.example.crispminds.R

class BookSessionFragmentViewModel(context: Context, coachDetailDTO: CoachDTO) : ViewModel() {

    var context: Context = context
    var coachDetailDTO: CoachDTO = coachDetailDTO

    var bookSessionVisibility = MutableLiveData<Boolean>(true)

    var selectedTabTextColor = MutableLiveData<String>("#8cc63f")
    var unSelecteTabTextColor = MutableLiveData<String>("#1d1d1d")
    var closeFragment = MutableLiveData<Boolean>(false)

    var date = MutableLiveData<String>()
    var startAndEndTime = MutableLiveData<String>()
    var startTime = MutableLiveData<String>("")
//    var endTime = MutableLiveData<String>("")
    var remark = MutableLiveData<String>()

    lateinit var createSessionDetails : CreateSessionDTO
    lateinit var saveSessionDetails : SaveSessionDetails

    var selectedBookAMeeting : Meeting? = null
    var selectedType : String? = null

    val showProgressBar = MutableLiveData<Boolean>()

    init {

        Log.i("BookSessionViewModel", "BookSessionFragmentViewModel init")

    }

    public fun onBookSessionClick(){
        bookSessionVisibility.value = true
    }

    public fun onSubsciptionPlanClick(){
        bookSessionVisibility.value = false
    }

    public fun onCheckAvailabilityClick(){
        if (validateData()){
            createSession()
//            Constants.showToastMessage(context,"Data Validate")
        }
    }

    public fun createSession(){
        showProgressBar.value = true

        coroutineScope.launch {

            var result = DashboardApi.retrofitService.createSession(createSessionDetails)
            try {
                var sessionDetailsDTO = result.await()
                Log.e("result ", sessionDetailsDTO.toString())

                /*val saveSessionDetails = SaveSessionDetails( SessoinDTO( "3 months",
                        "7 session","10000", "11/01/2020",
                    "08:00 - 09:00 AM", "Lorem 12, or lipsum") )*/

                var sessionDetailsResult = DashboardApi.retrofitService.saveSessionDetails(sessionDetailsDTO.id!!,saveSessionDetails)

                var saveSessionDetailsDTO = sessionDetailsResult.await()

                Log.e("saveSessionDetailsDTO ", saveSessionDetailsDTO.toString())
                showSuccessDialog()
//                Constants.showToastMessage(context,"Session created successfully")

//                .supportFragmentManager.popBackStack()
                showProgressBar.value = false
            }catch (e : Exception){
                Log.e("Error", e.toString())
                showProgressBar.value = false
                Constants.showToastMessage(context,e.toString())
            }
        }
    }

    fun onSessionSelected(type : String,bookAMeeting: Meeting, selected : Boolean){
        this.selectedType = type
        this.selectedBookAMeeting = bookAMeeting
    }

    fun validateData() : Boolean{

        var isValid = true

        if (selectedType.isNullOrEmpty() && selectedBookAMeeting == null){
            Constants.showToastMessage(context,"Please Select Session/Subscribe Plan")
            isValid = false
        }else if (selectedType.equals(Constants.BOOK_SESSION)){
            if (date.value.isNullOrEmpty()){
                Constants.showToastMessage(context,"Please Select Date")
                isValid = false

            }else if (startTime.value.isNullOrEmpty()){
                Constants.showToastMessage(context,"Please Select Time")
                isValid = false

            }else if (remark.value.isNullOrEmpty()){
                Constants.showToastMessage(context,"Please enter remark")
                isValid = false
            }else{
                createRequest()
            }
        }else if (selectedType.equals(Constants.SUBSCRIBE_PLAN)){
            if (remark.value.isNullOrEmpty()){
                Constants.showToastMessage(context,"Please enter remark")
                isValid = false
            }else{
                createRequest()
            }
        }
        return isValid
    }

    fun createRequest(){

        var categoryId : Long

        if (selectedType.equals(Constants.BOOK_SESSION)){
            categoryId = 35
        }else{
            categoryId = 36
        }

        createSessionDetails = CreateSessionDTO(selectedBookAMeeting!!.meetingType,Constants.BOOK_SESSION_STATUS,
            "Description Here 12...", listOf(categoryId))

        saveSessionDetails = SaveSessionDetails(
            SessoinDTO(
                selectedBookAMeeting!!.meetingType,
                selectedBookAMeeting!!.durationMinutes,
                selectedBookAMeeting!!.amount,
                date.value,
                startTime.value,
                remark.value
            )
        )

    }

    fun showSuccessDialog(){
        val builder = AlertDialog.Builder(context)
        //set message for alert dialog
        builder.setMessage(R.string.session_created_successfully)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Ok"){dialogInterface, which ->
            dialogInterface.dismiss()
            closeFragment.value = true
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}