package com.example.crispminds.view.mysession.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.MySessionDTO
import com.example.crispminds.models.MySessionMainDTO
import com.example.crispminds.utils.Constants
import com.example.opposfeapp.utils.GenericBaseObservable


class MySessionViewModel : GenericBaseObservable {

    private val sharedPreferences: SharedPreferences
    private val mApplication: Application
    var mySessionMutableLiveDataList = MutableLiveData<List<MySessionMainDTO>>()

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
        sharedPreferences = Constants.getSharedPreferences(application.applicationContext)

    }


    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {

/*
        Toast.makeText(
            mApplication.applicationContext,
            radioGroup?.findViewById<RadioButton>(id)?.text.toString(),
            Toast.LENGTH_SHORT
        )
            .show()
*/
    }

    fun loadLocalData() {

        val mySessionMainDTOList = ArrayList<MySessionMainDTO>()
        for (i in 0..3) {

            val mySessionDTOList = ArrayList<MySessionDTO>()
            mySessionDTOList.add(MySessionDTO((i+1).toLong(), "26", "10:30", "Ganesh Wagmare", "Financing",null))
            mySessionDTOList.add(MySessionDTO((i+2).toLong(), "27", "16:30", "Rahul Potghan", "Career Advice",null))

            val mySessionDTO = MySessionDTO(i.toLong(), "11", "09:30", "Sagar More", "Scholarship",mySessionDTOList)
            val mySessionMainDTO = MySessionMainDTO(i.toLong(), mySessionDTO)
            mySessionMainDTOList.add(mySessionMainDTO)
        }

        mySessionMutableLiveDataList.value = mySessionMainDTOList

    }



}