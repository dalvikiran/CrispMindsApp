package com.example.crispminds.models

import java.io.Serializable

data class VideoDetailDTO(
    val id: Long,
    val title: String,
    val youtube_video_link: String? = "https://img.youtube.com/vi/HDqia0fS2wE/default.jpg",
//    val video_uploaded_date : String,
//    val video_uploaded_by : String,
    val author: String,
    val content: String
) : Serializable




data class VideoDTO(
    val video: String
)
class VideoMasterDTO(

    val videoId: Long?,
    val coachId: Long?,
    val coachName: String?,
    val videoTitle: String?,
    val videoDesc: String?,
    val videoLinkId: String? = "HDqia0fS2wE",
    val videoTags: String?,
    val modifiedOn: String?,
    val createdOn: String?,
    val modifiedBy: Long?,
    val createdBy: Long?


) : Serializable




