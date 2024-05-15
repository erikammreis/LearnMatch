package br.com.fiap.learnmatch

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object StaticMethods {
    public fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
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


}