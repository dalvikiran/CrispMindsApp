package com.example.crispminds.source.sub_category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crispminds.models.categorydtos.CategoryDTO

@Dao
interface CategoryDao {

    /**
     * Insert a gender in the database. If the user already exists, replace it.
     *
     * @param category the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(vararg category: CategoryDTO)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(categoryList: List<CategoryDTO>)

    @Query("select * from categories")
    fun getSubcategory(): List<CategoryDTO>

    @Query("select * from categories where categoryName = :tag ")
    fun getSubcategoryByDescription(tag: String): List<CategoryDTO>

    @Query("delete from categories")
    fun deleteAllSubcategory()


}