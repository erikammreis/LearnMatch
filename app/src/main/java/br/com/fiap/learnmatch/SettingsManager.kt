package br.com.fiap.learnmatch

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

object SettingsManager {
    private const val SETTINGS_FILE_NAME = "settings.json"

    public fun getSettings(context: Context): SettingsUser {
        val jsonString = readSettingsFromFile(context)
        return Gson().fromJson(jsonString, SettingsUser::class.java) ?: SettingsUser()
    }

    public fun setSettings(context: Context, newSettings: SettingsUser) {
        val json = Gson().toJson(newSettings)
        writeSettingsToFile(context, json)
    }

    public fun readSettingsFromFile(context: Context): String {
        return try {
            val file = File(context.filesDir, SETTINGS_FILE_NAME)
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

    public fun writeSettingsToFile(context: Context, json: String) {
        try {
            val file = File(context.filesDir, SETTINGS_FILE_NAME)
            FileWriter(file).use { it.write(json) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


