package com.example.crispminds.view.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crispminds.R
import com.example.opposfeapp.utils.SingleLiveEvent
import kotlinx.android.synthetic.main.fragment_journal.view.*

class HomeFragmentViewModel : ViewModel() {

    val onVideoClickEvent = SingleLiveEvent<Void>()
    val onCoachesClickEvent = SingleLiveEvent<Void>()
    val onSessionClickEvent = SingleLiveEvent<Void>()
    val onJournalClickEvent = SingleLiveEvent<Void>()
    val onRewardsClickEvent = SingleLiveEvent<Void>()

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() =_name

    val _offerList = MutableLiveData<List<Int>>()
//    val offerList: LiveData<List<Int>>
//        get() = _offerList

    init {
        _name.value = "Home"
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeFragmentViewModel", "HomeFragmentViewModel destroyed!")
    }

    fun getDashboardData(){

        var offersList = listOf<Int>(R.drawable.offer_img_1,R.drawable.offer_img_2)

        _offerList.value = offersList

    }


    fun onCoachesClick(){
        onCoachesClickEvent.call()
    }

    fun onSessionClick(){
        onSessionClickEvent.call()
    }

    fun onVideoClick(){
        onVideoClickEvent.call()
    }

    fun onJournalClick(){
        onJournalClickEvent.call()
    }
    fun onRewardsClick(){
        onRewardsClickEvent.call()
    }
}