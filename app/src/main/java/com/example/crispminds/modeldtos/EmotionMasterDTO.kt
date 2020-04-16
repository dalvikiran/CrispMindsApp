package com.example.crispminds.modeldtos

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.crispminds.models.ImageDTO

@Entity(tableName = "emotion_master")
class EmotionMasterDTO(

    @PrimaryKey
    @ColumnInfo
    @NonNull
    var emotionId: Long? = 0L,

    @ColumnInfo
    @NonNull
    var emotionName: String? = "",

    @ColumnInfo
    @NonNull
    var sequenceNo: Long? = null,

    @ColumnInfo
    var selectDto: ArrayList<ImageDTO>? = null,

    @ColumnInfo
    var unSelectDto: ArrayList<ImageDTO>? = null
)

data class ImageCategoryDTO(
    val unselected_image: ArrayList<ImageDTO>?,
    val selected_image: ArrayList<ImageDTO>?
)