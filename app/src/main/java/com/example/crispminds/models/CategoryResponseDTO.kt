package com.example.crispminds.models

import com.example.crispminds.models.categorydtos.CategoryDTO

data class CategoryResponseDTO(
    val success : SuccessDTO?,
    val error : ErrorDTO?,
    val userId : Long?,
    val CategoryMasterList : List<CategoryDTO>
)