package com.example.crispminds.source.sub_category

import androidx.room.TypeConverter
import com.example.crispminds.models.ImageDTO
import com.example.crispminds.models.categorydtos.ImageCategoryDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageDTOConverter {

    /**
     * Convert a image object to a Json
     */
    @TypeConverter
    fun fromImageSubCategoryJson(image : ImageDTO?): String{
        if(image == null)
            return ""
        return Gson().toJson(image)
    }

    /**
     * Convert a json to a Image
     */
    @TypeConverter
    fun toImageSubCategory(jsonImage: String): ImageDTO? {
        if (jsonImage == null || jsonImage == "")
            return null
        val notesType = object : TypeToken<ImageDTO>() {}.type
        return Gson().fromJson<ImageDTO>(jsonImage, notesType)
    }
}