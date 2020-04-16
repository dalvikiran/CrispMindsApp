package com.example.crispminds.models

data class RegistrationRequestDTO(
    var userId: Long?,
    var userFirstName: String?,
    var userLastName: String?,
    var username: String?,
    var emailAddress: String?,
    var mobileNo: String?,
    var password: String?,
    var otp: String?,
    var roles: List<Int>? = listOf(2)
)