package com.example.crispminds.models

data class UserData(
    var data: UserDTO

)

data class AccessTokenDTO(
    val access_token : String,
    val token_type : String,
    val username : String,
    val user : UserDTO

)


data class UserDTO(
    var userId: Long,
    var emailAddress: String,
    var lastLoginDateTime: String,
    var loginId: String,
    var mobileNo: String,
    var userFirstName: String,
    var userLastName: String
)

data class EmailExitsStatus(var Status: Boolean?, var UserId: Long?)

data class SendOtpToEmail(var email: String, var otp: String)