package com.example.crispminds.retrofit_services.network_utils

interface IDataSourceCallback<T> {
    fun onDataFound(data: T) {}
    fun onDataFound(data: T, responseCode: Int) {}
    fun onDataNotFound() {}
    fun onError(error: String?)
}