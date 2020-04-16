package com.example.crispminds.models.categorydtos

import java.io.Serializable


data class VideoCategoryDTO(
    val id: Long,
    val acf: VideoDataDTO?
): Serializable