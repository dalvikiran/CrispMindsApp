package com.example.crispminds.retrofit_services.services

import com.example.crispminds.models.*
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.models.categorydtos.CategoryDTO_old
import com.example.crispminds.models.categorydtos.VideoCategoryDTO
import com.example.crispminds.models.categorydtos.journal.AddNewJournalDTO
import com.example.crispminds.models.categorydtos.journal.JournalMainCategoryDTO
import com.example.crispminds.models.categorydtos.journal.SaveOrUpdateJournalDetails
import com.example.crispminds.models.categorydtos.journal.UpdateJournalDTOById
import com.example.crispminds.retrofit_services.network_utils.*
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface DashboardService {


    @POST(COACHES_LIST_URL)
    fun getCoachesList(): Deferred<CoachListResponse>
//    fun getCoachesList(@Query("sortOrder") sortOrder: String,@Query("sortOrder") sortField: String, @Query("sortOrder") booleanfield: Boolean): Deferred<CoachListResponse>
//    fun getCoachesList(@Query("categories") categoryId: Long,@Query("tags") subCategory: Long): Deferred<List<CoachDetailDTO>>

    @POST(COACHES_DETAILS_URL)
    fun getCoacheDetails(@Path("id") coacheId: Long): Deferred<CoachDetailResponse>

    /*@GET(JOURNAL_LIST_URL)
    fun getJournalList(): Call<ResponseBody>*/

    // Get all Categories data
    @GET(CATEGORIES_LIST_URL)
    fun getCategoriesList(@Query("parent") parent: Long): Deferred<List<CategoryDTO_old>>

    // Get all Categories data
    @GET(GET_ALL_SUB_CATEGORIES_LIST_URL)
    fun getAllSubCategoriesList(): Deferred<List<CategoryDTO>>

    // Get all Categories data
    @GET(CATEGORIES_LIST_URL)
    fun getImageCategoriesList(@Query("parent") parent: Long): Deferred<List<CategoryDTO>>

    // Get video sub categories data
    @GET(SUB_CATEGORIES_LIST_URL)
    fun getVideosDataList(
        @Query("categories") categoryId: Long,
        @Query("tags") subCategory: Long
    ): Deferred<List<VideoCategoryDTO>>

    // Get coaches sub categories data
    @GET(SUB_CATEGORIES_LIST_URL)
    fun getCoachesDataList(@Query("categories") parent: Long): Deferred<List<VideoCategoryDTO>>

    /** Get Journal sub categories list data*/
    @GET(SUB_CATEGORIES_LIST_URL)
    fun getJournalDataList(@Query("categories") parent: Long): Deferred<List<JournalMainCategoryDTO>>

    /** Add Journal to list data*/
    @POST(SUB_CATEGORIES_LIST_URL)
    fun addNewJournalData(
        @Header("Content-Type") contentType: String,
        @Body body: AddNewJournalDTO
    ): Deferred<UpdateJournalDTOById>

    /** Add Journal to list data*/
    @POST(UPDATE_JOURNAL_URL)
    fun updateJournalData(
        @Header("Content-Type") contentType: String,
        @Path("id") id: Long,
        @Body fields: SaveOrUpdateJournalDetails
    ): Deferred<JournalMainCategoryDTO>

    /** Add Journal to list data*/
    @DELETE(DELETE_JOURNAL_URL)
    fun deleteJournalData(@Path("id") id: Long): Deferred<JournalMainCategoryDTO>

    // create session
    @POST(CREATE_SESSION_URL)
    fun createSession(@Body createSessionDTO: CreateSessionDTO): Deferred<SessionDetailsDTO>

    @POST(SAVE_SESSION_DETAILS_URL)
    fun saveSessionDetails(
        @Path("sessionId") sessionId: Long,
        @Body saveSessionDetails: SaveSessionDetails
    ): Deferred<SessionDetailsDTO>


    /** -------------------------------------------- NEW APIs Services -------------------------------------------------*/

    @POST(CATEGORY_DETAILS_URL)
    fun fetchCategoryDetails(): Call<ResponseBody>

    @POST(EMOTION_MASTERS_URL)
    fun fetchEmotionMasters(
        @Header("headers") headers: String,
        @Header("Content-Type") contentType: String,
        @Query("draw") draw: Int,
        @Query("start") start: Int,
        @Query("length") length: Int,
        @Query("columns") columns: String,
        @Query("search") search: String,
        @Query("sortOrder") sortOrder: String,
        @Query("sortField") sortField: String,
        @Query("searchCol") searchCol: String,
        @Query("booleanfield") booleanfield: Boolean
    ): Call<ResponseBody>


    @POST(FETCH_VIDEO_LISTS_URL)
    fun fetchVideosListByCategoryId(
        @Header("headers") headers: String,
        @Header("Content-Type") contentType: String,
        @Path("id") id: Long,
        @Query("draw") draw: Int,
        @Query("start") start: Int,
        @Query("length") length: Int,
        @Query("columns") columns: String,
        @Query("search") search: String,
        @Query("sortOrder") sortOrder: String,
        @Query("sortField") sortField: String,
        @Query("searchCol") searchCol: String,
        @Query("booleanfield") booleanfield: Boolean

    ): Call<ResponseBody>


    @POST(FETCH_VIDEO_DETAILS_URL)
    fun fetchVideosDetailsByVideoId(
        @Header("headers") headers: String,
        @Path("id") videoId: Long
    ): Call<ResponseBody>


}
