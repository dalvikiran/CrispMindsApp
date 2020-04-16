package com.example.crispminds.view.dashboard.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.models.UserDTO
import com.example.crispminds.utils.Constants
import com.example.opposfeapp.utils.GenericBaseObservable


class ProfileFragmentViewModel : GenericBaseObservable {

    private var mApplication: Application
    var addKidsLayoutVisibility = MutableLiveData<Boolean>(false)
    var addFamilyMembersLayoutVisibility = MutableLiveData<Boolean>(true)
    private var sharedPreferences: SharedPreferences
    private var userDto: UserDTO? = null
    var userNameMutableLiveData = MutableLiveData<String>("")

    var rewardPoints = MutableLiveData<Int>(100)

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
        userDto = Constants.getUserDTO(
            Constants.decrypt(
                sharedPreferences.getString(
                    Constants.USER_DTO,
                    null
                )
            )
        )

        userNameMutableLiveData.value = userDto?.userFirstName + " " + userDto?.userLastName



    }

    fun onShowAddKidsLayout() {
//        addKidsLayoutVisibility.value = !addKidsLayoutVisibility.value!!

        rewardPoints.value = rewardPoints.value?.plus(100)
    }

    fun onShowAddFamilyMembersLayout() {
        addFamilyMembersLayoutVisibility.value = !addFamilyMembersLayoutVisibility.value!!
    }


}