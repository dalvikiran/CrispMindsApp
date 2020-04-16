package com.example.crispminds.models

import java.io.Serializable

data class SuccessDTO(
    val status : String,
    val message : String
) : Serializable


data class ErrorDTO(
    val errorCode : String,
    val message : String
) : Serializable
