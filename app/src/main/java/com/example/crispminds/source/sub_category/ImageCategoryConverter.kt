package com.example.crispminds.source.sub_category

import androidx.room.TypeConverter
import com.example.crispminds.models.categorydtos.ImageCategoryDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageCategoryConverter {

    /**
     * Convert a image object to a Json
     */
    @TypeConverter
    fun fromImageSubCategoryJson(image : ImageCategoryDTO?): String{
        if(image == null)
            return ""
        return Gson().toJson(image)
    }

    /**
     * Convert a json to a Image
     */
    @TypeConverter
    fun toImageSubCategory(jsonImage: String): ImageCategoryDTO? {
        if (jsonImage == null || jsonImage.equals(""))
            return null
        val notesType = object : TypeToken<ImageCategoryDTO>() {}.type
        return Gson().fromJson<ImageCategoryDTO>(jsonImage, notesType)
    }
}