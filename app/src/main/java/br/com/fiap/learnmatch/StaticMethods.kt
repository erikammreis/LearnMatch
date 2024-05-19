package br.com.fiap.learnmatch

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object StaticMethods {
    private const val USER_INFO_FILE_NAME = "user.json"
    public fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.parse(dateString)
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    public fun isDateValid(date: Date): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false // Isso fará com que a validação da data seja estrita

        try {
            val currentDate = Date()

            if (date != null && date.before(currentDate)) {
                return false
            }
        } catch (e: ParseException) {
            return false
        }

        return true
    }


    fun getJsonUserDate(context: Context, user: UserData):String {
        setUserInf(context, user)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(user)

    }

    fun getJsonUser(context: Context, user: User):String {
        setUserInf(context, user)
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(user)

    }
    private  fun setUserInf(context: Context, userInfo: User) {
        val json = Gson().toJson(userInfo)
        writeUserInfoToFile(context, json)
    }

    public fun getUserInf(context: Context): User {
        val jsonString = readUserInfoFromFile(context)
        return Gson().fromJson(jsonString, User::class.java) ?: User()
    }

    private  fun setUserInf(context: Context, userInfo: UserData) {
        val json = Gson().toJson(userInfo)
        writeUserInfoToFile(context, json)
    }


    private fun readUserInfoFromFile(context: Context): String {
        return try {
            val file = File(context.filesDir, USER_INFO_FILE_NAME)
            if (file.exists()) {
                file.readText()
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private  fun writeUserInfoToFile(context: Context, json: String) {
        try {
            val file = File(context.filesDir, USER_INFO_FILE_NAME)
            FileWriter(file).use { it.write(json) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}