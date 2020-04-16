package com.example.crispminds.models


data class MySessionMainDTO(
    var id: Long,
    var acf: MySessionDTO?
)

data class MySessionDTO(
    var statusId: Long,
    var date: String?,
    var scheduledAtTime: String?,
    var scheduledForCoachName: String?,
    var sessionOfCategoryName: String?,
    var list: List<MySessionDTO>?
)
