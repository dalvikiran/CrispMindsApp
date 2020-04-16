package com.example.crispminds.models

data class CreateSessionDTO(
    val title :  String,
    val status :  String,
    val content :  String,
    val categories :  List<Long>
)

data class SessionDetailsDTO(
    val id :  Long?,
    val acf :  SessoinDTO
)

data class SessoinDTO(
    val book_type : String?,
    val minutes_time : String?,
    val amount : String?,
    val check_availabilty_date : String?,
    val check_availability_time_range : String?,
    val remark : String?
)

data class SaveSessionDetails(
    val fields : SessoinDTO
)