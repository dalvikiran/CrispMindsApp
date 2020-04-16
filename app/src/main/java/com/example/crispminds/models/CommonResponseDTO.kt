package com.example.crispminds.models

import java.io.Serializable

class CommonResponseDTO (
    val success : SuccessDTO?,
    val error : ErrorDTO?
) : Serializable