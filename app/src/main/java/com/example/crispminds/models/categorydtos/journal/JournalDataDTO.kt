package com.example.crispminds.models.categorydtos.journal

import java.io.Serializable

data class JournalDataDTO(

    var journal_date: String?,
    var journal_time: String?,
    var journal_heading: String?,
    var journal_details: String?,
    var journal_date_day: String? = "",
    var journal_date_dd: String? = "",
    var journal_smiley_id: Long? = 0L,
    var journal_smiley_name: String? = ""
//    var journal_category_List: ArrayList<CategoryDTO_old>?

) : Serializable


data class SaveOrUpdateJournalDetails(
    val fields : JournalDataDTO
)