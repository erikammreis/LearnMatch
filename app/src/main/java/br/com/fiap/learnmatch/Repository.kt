package br.com.fiap.learnmatch

import android.content.Context
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(private val context: Context) {
    private val retrofit: Retrofit = buildRetrofit()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://66451180b8925626f890f180.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getStudentsFromApi(callback: Callback<List<UserData>>) {
        val call = apiService.getStudents()
        call.enqueue(callback)
    }

    fun getNotificationsFromApi(callback: Callback<List<NotificationData>>) {
        val call = apiService.getNotifications()
        call.enqueue(callback)
    }

    fun getMentorsFromApi(callback: Callback<List<UserData>>) {
        val call = apiService.getMentors()
        call.enqueue(callback)
    }
    fun addJsonToFile(json: String){
        try {
            val userData = Gson().fromJson(json, UserData::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}