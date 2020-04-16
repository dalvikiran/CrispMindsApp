package com.example.crispminds.models.videosdto

import java.io.Serializable

class VideoMasterDTO(

    val categoryId: Long?,
    val videoId: Long?,
    val coachId: Long?,
    val coachName: String?,
    val videoTitle: String?,
    val date: String?,
    val time: String?,
    val videoDesc: String?,
    val videoLinkId: String? = "HDqia0fS2wE",
    val videoTags: String?,
    val modifiedOn: String?,
    val createdOn: String?,
    val modifiedBy: Long?,
    val createdBy: Long?,
//    val videoLikesCount: Long?,
    val videoLikesCount: String?,
    val userVideoLike: Boolean?


) : Serializable