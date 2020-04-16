package com.example.crispminds.models.categorydtos.journal

import java.io.Serializable

data class AddNewJournalDTO(
    var title: String = "My test demo with journal",
    var status: String = "publish",
    var categories: List<Int>
) : Serializable