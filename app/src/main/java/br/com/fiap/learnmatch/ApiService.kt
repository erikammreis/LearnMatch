package br.com.fiap.learnmatch

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("students")
    fun getStudents(): Call<List<UserData>>

    @GET("mentors")
    fun getMentors(): Call<List<UserData>>

    @POST("register")
    fun postRegister(json: Gson ): Boolean

    @PUT("register")
    fun putUpdateUser(json: Gson): Boolean




}
