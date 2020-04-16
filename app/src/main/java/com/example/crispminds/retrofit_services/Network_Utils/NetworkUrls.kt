package com.example.crispminds.retrofit_services.network_utils

const val SERVER_URL: String = "http://103.143.46.76:5009/crispminds/"


//const val GET_YOUTUBE_VIDEO_THUMBNAIL_URL : String = "https://img.youtube.com/vi/{videoId}/0.jpg"
const val GET_YOUTUBE_VIDEO_THUMBNAIL_URL: String = "https://img.youtube.com/vi/"
const val YOUTUBE_VIDEO_THUMBNAIL_IMAGE_NAME: String = "/0.jpg"


/**---------------------------------- New APIs -------------------------------------*/

const val LOGIN_URL: String = "loginWithLoginId"
const val REGISTER_URL : String = "addUser"
const val VERIFY_OTP_URL : String = "loginWithOTP"
const val SET_PASSWORD_URL : String = "resetPassword"
const val FORGOT_PASSWORD_URL: String = "forgotPassword"

const val CATEGORY_DETAILS_URL: String = "getAllCategoryPagination"
const val EMOTION_MASTERS_URL: String = "getAllEmotionPagination"

const val COACHES_LIST_URL: String = "asset-service/getAllCoachPagination"       //""wp-json/wp/v2/posts"
const val COACHES_DETAILS_URL: String = "asset-service/findCoachById/{id}"


const val FETCH_VIDEO_LISTS_URL: String = "getAllVideoPagination/{id}"
const val FETCH_VIDEO_DETAILS_URL: String = "findVideoById/{id}"






/*
 Word Press
*/

//const val SERVER_URL: String = "http://37.187.78.170:8080"
//const val SERVER_URL: String = "http://103.143.46.76:9001"

//const val LOGIN_URL: String = "wp-json/custom-plugin/login"
const val CHECK_EMAIL_EXIST_URL: String = "wp-json/checkmail/exist"
const val GENERATE_OTP_URL: String = "wp-json/sendmail/otp"

//const val REGISTER_URL: String = "wp-json/wp/v2/users/register"

//const val VIDEO_LIST_URL : String = "wp-json/videodetails/v2/posts"
//const val JOURNAL_LIST_URL : String = "wp-json/videodetails/v2/posts"

const val CATEGORIES_LIST_URL: String = "wp-json/wp/v2/categories"
const val SUB_CATEGORIES_LIST_URL: String = "wp-json/wp/v2/posts"
const val GET_ALL_SUB_CATEGORIES_LIST_URL: String = "wp-json/wp/v2/tags"
const val UPDATE_JOURNAL_URL: String = "wp-json/acf/v3/posts/{id}"
const val DELETE_JOURNAL_URL: String = "wp-json/wp/v2/posts/{id}"

const val CREATE_SESSION_URL: String = "wp-json/wp/v2/posts"
const val SAVE_SESSION_DETAILS_URL: String = "wp-json/acf/v3/posts/{sessionId}"
const val RESULT_OK: Int = 200



