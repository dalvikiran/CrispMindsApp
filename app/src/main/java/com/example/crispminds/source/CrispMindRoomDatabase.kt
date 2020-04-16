package com.example.crispminds.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crispminds.master_controller.source.daos.EmotionMasterDao
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.source.sub_category.CategoryDao
import com.example.crispminds.source.sub_category.ImageCategoryConverter
import com.example.crispminds.source.sub_category.ImageDTOConverter
import com.example.crispminds.source.sub_category.ImageListDTOConverter
import com.example.crispminds.utils.Constants

// Annotates class to be a Room Database with a table (entity) of the SubCategory class
@Database(
    entities = [CategoryDTO::class, EmotionMasterDTO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImageCategoryConverter::class,
    ImageDTOConverter::class,
    ImageListDTOConverter::class
)
abstract class CrispMindRoomDatabase : RoomDatabase() {

    abstract fun subCategoryDao(): CategoryDao
    abstract fun emotionDao(): EmotionMasterDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CrispMindRoomDatabase? = null

        fun getDatabase(context: Context): CrispMindRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CrispMindRoomDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

        /* private val LOCK = Any()

         operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCK){
             INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
         }

         private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
             CrispMindRoomDatabase::class.java, Constants.DATABASE_NAME)
             .build()*/
    }
}