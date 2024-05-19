package br.com.fiap.learnmatch

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("students")
    fun getStudents(): Call<List<UserData>>

    @GET("mentors")
    fun getMentors(): Call<List<UserData>>

    @GET("notification")
    fun getNotifications(): Call<List<NotificationData>>

    @POST("register")
    fun postRegister(json: Gson ): Boolean

    @PUT("register")
    fun putUpdateUser(json: Gson): Boolean




}
