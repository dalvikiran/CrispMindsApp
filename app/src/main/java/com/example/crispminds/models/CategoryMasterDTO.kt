package com.example.crispminds.models

data class CategoryMasterDTO (
    val categoryId : Long,
    val categoryName : String,
    val sequenceNo : Long,
    val categoryColor : String,
    val documentGetDto : List<ImageDTO>
)