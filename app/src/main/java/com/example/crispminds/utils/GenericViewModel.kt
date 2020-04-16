package com.example.crispminds.utils

import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import java.util.*

open class GenericViewModel : ViewModel(), Observable {

    private val registry: PropertyChangeRegistry =  PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) = registry.add(callback)

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) = registry.remove(callback)

    fun notifyChange() {
        registry.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        registry.notifyCallbacks(this, fieldId, null)
    }
}