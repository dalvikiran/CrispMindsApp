package com.example.crispminds.models.categorydtos

import java.io.Serializable

data class VideoDataDTO(

    val youtube_video_link: String?,
    val video_uploaded_by: String?,
    val video_title: String?,
    val video_uploaded_date: String?,
    val video_uploded_time: String?,
    val video_description: String?,
    val video_coach_name: String?

) : Serializable