package com.example.crispminds.utils

import java.util.regex.Pattern

class ErrorCheckUtils {

    companion object{
        val EMAIL_REGEX = "[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*"
        val passwordRegex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}")
        val passwordSpecialSymboleRegex = Pattern.compile("^(?=.*[@#\$%!\\-_?&])")
        val passwordCapitalLetterRegex = Pattern.compile("^(?=.*[A-Z])")
        val passwordNumberRegex = Pattern.compile("^(?=.*[0-9])")


        fun checkValidUserName(userName: String?): String {
            return when {
                userName == null -> "Enter valid User Name"
                userName.trim { it <= ' ' }.isEmpty() -> "Please enter valid User Name"
                userName.trim { it <= ' ' }.length < 5 -> "User Name is too short"
                else -> ""
            }
        }

        fun checkValidFirstName(userName: String?): String {
            return when {
                userName == null -> "Enter valid User Name"
                userName.trim { it <= ' ' }.isEmpty() -> "Please enter valid first name"
                userName.trim { it <= ' ' }.length < 5 -> "First Name is too short"
                else -> ""
            }
        }

        fun checkValidLastName(userName: String?): String {
            return when {
                userName == null -> "Enter valid User Name"
                userName.trim { it <= ' ' }.isEmpty() -> "Please enter valid last name"
//                userName.trim { it <= ' ' }.length < 5 -> "User Name is too short"
                else -> ""
            }
        }

        fun checkValidPassword(password: String?): String {
            return when {
                password == null -> "Please enter Password"
                password.isEmpty() -> "Please enter Password"
//                !password.trim().matches(passwordSpecialSymboleRegex.toRegex()) -> "Password must have special symbol"
//                !password.trim().matches(passwordNumberRegex.toRegex()) -> "Password must have number"
//                !password.trim().matches(passwordCapitalLetterRegex.toRegex()) -> "Password must have Capital letters"
//                !password.trim().matches(passwordRegex.toRegex()) -> "Password must have characters, special symbols and numbers"
                password.length < 4 -> "Password is too short"
                else -> ""
            }

        }

        fun checkValidEmail(email: String?): String {
            return if (email == null) {
                "Please enter valid email address"
            } else if (email.trim { it <= ' ' }.isEmpty()) {
                "Please enter valid email address"
            } else if (email.trim { it <= ' ' }.length < 6) {
                "Email id is too short"
            } else if (!email.trim { it <= ' ' }.matches(EMAIL_REGEX.toRegex())) {
                "Enter valid email id"
            } else {
                ""
            }
        }

        fun checkValidMobile(number: String?): String {
            if (number == null)
                return "Please enter mobile number"
            else if (number.trim { it <= ' ' }.isEmpty()) {
                return "Please enter mobile number"
            } else if (number.trim { it <= ' ' }.length < 10) {
                return "Please enter correct mobile number"
            } else if (!number.matches("^[6789]\\d{9}$".toRegex())) {
                return "Please enter valid mobile number"
            }else {
                ""
            }

            try {
                java.lang.Long.parseLong(number)
            } catch (ex: NumberFormatException) {
                return "Please enter correct mobile number"
            }

            return ""
        }



        fun checkValidOtp(otp: String?): String {
            return when {
                otp == null -> "Please enter valid otp"
                otp.trim { it <= ' ' }.isEmpty() -> "Please enter valid otp"
                otp.trim { it <= ' ' }.length < 4 -> "Otp is too short"
                else -> ""
            }
        }


    }

}