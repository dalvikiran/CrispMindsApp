package com.example.crispminds.models

import java.io.Serializable

data class RegistrationResponceDTO(
    val success : SuccessDTO?,
    val error : ErrorDTO?,
    val totalCount : Long?,
    val userId : Long?,
    val otp : String?
) : Serializable