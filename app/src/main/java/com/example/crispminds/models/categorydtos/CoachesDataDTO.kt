package com.example.crispminds.models.categorydtos

import java.io.Serializable

data class CoachesDataDTO(

    val coach_title: String,
    val about_the_coach_video: String,
    val about_the_coach_information: String,
    val videos: String,
    val specialization: String,
    val date: String,
    val time: String,
    val coach_likes: String,
    val coach_image: String,
    val testimonials: String,
    val certification_and_awards: String

) : Serializable