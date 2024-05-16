package br.com.fiap.learnmatch

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("students")
    fun getStudents(): Call<List<UserData>>
}
