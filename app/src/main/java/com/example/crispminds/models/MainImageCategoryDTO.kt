package com.example.crispminds.models

data class MainImageCategoryDTO(
    val id: Long?,
    val name: String?,
    val acf: ImageCategoryDTO?
)

data class ImageCategoryDTO(
    val unselected_image: ImageDTO?,
    val selected_image: ImageDTO?
)