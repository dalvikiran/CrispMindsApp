package com.example.crispminds.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.crispminds.R
import com.example.crispminds.models.RegistrationRequestDTO
import com.example.crispminds.models.UserDTO
import com.example.crispminds.models.VideoDTO
import com.example.crispminds.view.dialogs.VideoDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class Constants {

    companion object {


        private const val SHARED_PREF_NAME = "CripMindsSharedPreferences"
        const val ERROR_MSG = "Something went wrong on the server.\nPlease try again later..."

        const val DATABASE_NAME: String = "crisp_mind_database"

        const val IS_FIRST_TIME: String = "IS_FIRST_TIME"
        const val IS_QUETIONAIRE_DONE: String = "IS_QUETIONAIRE_DONE"

        const val USER_DTO: String = "USER_DTO"
        const val VIDEO_CATEGORY_ID: String = "VIDEO_CATEGORY_ID"
        const val COACHES_CATEGORY_ID: String = "COACHES_CATEGORY_ID"
        const val JOURNAL_CATEGORY_ID: String = "JOURNAL_CATEGORY_ID"
        const val VIDEO_CATEGORY_STRING = "Videos"
        const val COACHES_CATEGORY_STRING = "Coaches"
        const val JOURNAL_CATEGORY_STRING = "Journal"

        const val VIDEO_DETAILS = "Video_details"
        const val COACH_DETAILS = "Coach_details"
        const val JOURNAL_DETAILS = "Coach_details"

        const val SPLASH_TIME_OUT: Long = 1000 * 3 // 3 sec

        const val HAPPY_STRING = "happy"
        const val CONFUSED_STRING = "confused"
        const val SAD_STRING = "sad"
        const val ANGRY_STRING = "angry"
        const val CONTENT_TYPE = "application/json"

        const val BOOK_SESSION = "BOOK_SESSION"
        const val SUBSCRIBE_PLAN = "SUBSCRIBE_PLAN"
        const val BOOK_SESSION_STATUS = "publish"

        const val REGISTRATION_DATA = "REGISTRATION_DATA"
        const val USER_EMAIL_ADDRESS = "USER_EMAIL_ADDRESS"

        const val ACCESS_TOKEN = "ACCESS_TOKEN"

        const val ALL = "All"
        const val JOURNAL = "Journal"

        const val SOURCE = "APP"
        const val ROLE = "USER"


        private lateinit var sharedPreferences: SharedPreferences


        fun showToastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        inline fun <reified T : Activity> changeActivity(context: Context) {
            val intent = Intent(context, T::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun getVideoCategoryId(context: Context): String? {
            sharedPreferences = getSharedPreferences(context)
            return try {
                decrypt(sharedPreferences.getString(VIDEO_CATEGORY_ID, ""))
            } catch (e: Exception) {
                ""
            }
        }

        fun getCoachesCategoryId(context: Context): String? {
            sharedPreferences = getSharedPreferences(context)
            return try {
                decrypt(sharedPreferences.getString(COACHES_CATEGORY_ID, ""))
            } catch (e: Exception) {
                ""
            }
        }

        fun getIsFirstTime(context: Context?): Boolean? {
            sharedPreferences = getSharedPreferences(context!!)
            return try {
                sharedPreferences.getBoolean(IS_FIRST_TIME, true)
            } catch (e: Exception) {
                true
            }
        }

        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        }

        @Throws(Exception::class)
        fun encrypt(plainText: String?): String? {
            var encryptedText = ""
            if (plainText != null) {
                val secretKey: SecretKey = getKey()
                val plainTextByte = plainText.toByteArray()
                val cipher: Cipher = Cipher.getInstance("AES")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                val encryptedByte: ByteArray = cipher.doFinal(plainTextByte)
                //Base64.Encoder encoder = Base64.getEncoder();
                encryptedText = Base64.encodeToString(encryptedByte, Base64.URL_SAFE)
            }
            return encryptedText
        }

        @Throws(Exception::class)
        fun decrypt(encryptedText: String?): String? {
            var decryptedText: String?
            if (encryptedText != null && !encryptedText.equals("", ignoreCase = true)) {
                val secretKey: SecretKey = getKey()
                val cipher: Cipher = Cipher.getInstance("AES")
                // Base64.Decoder decoder = Base64.getDecoder();
                val encryptedTextByte: ByteArray =
                    Base64.decode(encryptedText, Base64.URL_SAFE)
                cipher.init(Cipher.DECRYPT_MODE, secretKey)
                val decryptedByte: ByteArray = cipher.doFinal(encryptedTextByte)
                decryptedText = String(decryptedByte)
                return decryptedText
            }
            return null
        }

        @Throws(NoSuchAlgorithmException::class)
        private fun getKey(): SecretKey {
            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            md.update("reliance@123".toByteArray())
            val encryptionKey: ByteArray = md.digest()
            return SecretKeySpec(encryptionKey, "AES")
        }


        fun getFormattedDD(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("dd")
            return formatString.format(date) ?: ""
        }


        fun getFormattedMonthNYear(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("MMMM yyyy")
            return formatString.format(date) ?: ""
        }

        fun getFormattedDayOfWeek(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("EEE")
            return formatString.format(date) ?: ""
        }


        fun getDatePickerFormattedDate(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd-MM-yyyy") // 01-01-2020
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("dd-MMM-yyyy") // 01-Jan-2020
            return formatString.format(date) ?: ""
        }

        fun getDatePickerFormattedDate2(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd/MM/yyyy") // 01-01-2020
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("dd-MMM-yyyy") // 01-Jan-2020
            return formatString.format(date) ?: ""
        }

        fun getDatePickerFormattedDate3(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd-MM-yyyy")// 01-01-2020
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("dd/MM/yyyy")// 01/01/2020
            return formatString.format(date) ?: ""
        }

        fun getDatePickerFormattedDate4(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("dd-MMM-yyyy")// 01-Jan-2020
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("dd/MM/yyyy")// 01/01/2020
            return formatString.format(date) ?: ""
        }


        fun getDatePickerFormattedTime(DOB: String?): String? {
            val format: DateFormat = SimpleDateFormat("hh:mm")// 00:00
            var date: Date? = null
            try {
                date = format.parse(DOB)
            } catch (e: ParseException) {
            }
            val formatString: DateFormat = SimpleDateFormat("hh:mm a")// 00:00 AM
            return formatString.format(date) ?: ""
        }


        fun selectDate(context: Context, mutableLiveData: MutableLiveData<String>) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    mutableLiveData.value =
                        getDatePickerFormattedDate(
                            dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year.toString()
                        )
                }, year, month, day
            )
            datePickerDialog.show()

        }

        fun selectTime(
            context: Context,
            startTime: MutableLiveData<String>
        ) {
            val cal = Calendar.getInstance()
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
//            val amPm = cal.get(Calendar.AM_PM)


            if (!startTime.value.isNullOrEmpty() ) {
                startTime.value = ""
            }

            val timePickerDialog = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    startTime.value = getDatePickerFormattedTime("$hourOfDay:$minute")
                }
                , hours, minutes, false)

            timePickerDialog.setTitle(context.resources.getString(R.string.select_time))
            timePickerDialog.show()
        }

        fun selectTime(
            context: Context,
            startTime: MutableLiveData<String>,
            endTime: MutableLiveData<String>
        ) {
            val cal = Calendar.getInstance()
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
//            val amPm = cal.get(Calendar.AM_PM)


            if (!startTime.value.isNullOrEmpty() && !endTime.value.isNullOrEmpty()) {
                startTime.value = ""
                endTime.value = ""
            }

            val timePickerDialog = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    if (startTime.value.isNullOrEmpty()) {
                        startTime.value = getDatePickerFormattedTime("$hourOfDay:$minute")
                        selectTime(context, startTime, endTime)
                    } else {
                        endTime.value = getDatePickerFormattedTime("$hourOfDay:$minute")
                    }
                }
                , hours, minutes, false)
            if (startTime.value.isNullOrEmpty()) {
                timePickerDialog.setTitle(context.resources.getString(R.string.select_start_time))
            } else {
                timePickerDialog.setTitle(context.resources.getString(R.string.select_end_time))
            }
            timePickerDialog.show()
        }


        fun getUserFromSharedPreference(value: String): UserDTO? {
            val homeCategoryList: Type =
                object : TypeToken<UserDTO?>() {}.getType()
            return Gson().fromJson(value, homeCategoryList)
        }

        fun saveUserDataInSharedPreference(mAccessoriesArrayList: UserDTO?): String? {
            val gson = Gson()
            return gson.toJson(mAccessoriesArrayList)?.toString()
        }

        fun generateOtp(): Int {
            return Random().nextInt(10000)
        }


        fun getUserDTO(value: String?): UserDTO? {
            val addressType = object : TypeToken<UserDTO?>() {}.type
            return Gson().fromJson(value, addressType)
        }

        fun setUserDTO(userDTO: UserDTO?): String? {
            val gson = Gson()
            return gson.toJson(userDTO)
        }

//        fun showVideoDialog(context : Context){
        fun showVideoDialog(context : Context,videoDTO: VideoDTO){

            var activity = context as Activity


        }

        fun getDrawable(context : Context,resource : Int): Drawable{

            return context.resources.getDrawable(resource)
        }


    }
}