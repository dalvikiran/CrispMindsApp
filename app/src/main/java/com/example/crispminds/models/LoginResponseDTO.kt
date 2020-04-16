package com.example.crispminds.models

data class LoginResponseDTO(
    val success : SuccessDTO?,
    val error : ErrorDTO?,
    val data : AccessTokenDTO

)