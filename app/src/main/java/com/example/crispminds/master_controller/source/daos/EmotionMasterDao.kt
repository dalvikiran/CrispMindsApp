package com.example.crispminds.master_controller.source.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crispminds.modeldtos.EmotionMasterDTO

@Dao
interface EmotionMasterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(emotionArrayList: List<EmotionMasterDTO>)

    @Query("select * from emotion_master")
    fun getAllEmotions(): List<EmotionMasterDTO>

    @Query("delete from emotion_master")
    fun deleteAllEmotions()

    @Query("select * from emotion_master where emotionName = :emotionName ")
    fun getEmotionDetailsByEmotionName(emotionName: String): List<EmotionMasterDTO>

    @Query("select * from emotion_master where emotionId = :emotionId ")
    fun getEmotionDetailsByEmotionId(emotionId: Long): List<EmotionMasterDTO>


}