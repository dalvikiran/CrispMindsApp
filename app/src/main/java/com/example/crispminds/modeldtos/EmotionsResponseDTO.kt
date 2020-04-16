package com.example.crispminds.modeldtos

import com.example.crispminds.models.ErrorDTO
import com.example.crispminds.models.SuccessDTO

data class EmotionsResponseDTO(
    val success: SuccessDTO?,
    val error: ErrorDTO?,
    val userId: Long?,
    val EmotionMasterList: ArrayList<EmotionMasterDTO>
)