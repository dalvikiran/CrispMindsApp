package com.example.crispminds.source.sub_category

import android.util.Log
import com.example.crispminds.models.categorydtos.CategoryDTO


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CategoryRepository(private val categoryDao: CategoryDao){

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    fun getCategorie(tag : String) : List<CategoryDTO> {
        return  categoryDao.getSubcategoryByDescription(tag)
//        return  categoryDao.getAllSubcategory()
    }

    suspend fun insertData(categoryList : List<CategoryDTO>){
        try {

//            categoryDao.deleteAllSubcategory()
            categoryDao.insert(categoryList)

        }catch (e:Exception){
            Log.e("insert_error",e.toString())
        }
    }
}