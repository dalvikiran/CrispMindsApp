package com.example.crispminds.models.categorydtos

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.crispminds.models.ImageDTO

@Entity(tableName = "categories")
class CategoryDTO(
    @PrimaryKey
    @ColumnInfo
    @NonNull
    val categoryId : Long,

    @ColumnInfo
    @NonNull
    val categoryName : String,

    @ColumnInfo
    @NonNull
    val categoryColor : String,

    @ColumnInfo
    @NonNull
    val sequenceNo : Long,

    @ColumnInfo
//    @NonNull
    val documentGetDto: ImageDTO?

)

data class ImageCategoryDTO(
    val unselected_image: ImageDTO?,
    val selected_image: ImageDTO?
)