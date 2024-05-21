package br.com.fiap.learnmatch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("students")
    fun getStudents(): Call<List<UserData>>

    @GET("mentors")
    fun getMentors(): Call<List<UserData>>

    @GET("notification")
    fun getNotifications(): Call<List<NotificationData>>
    @GET("feed")
    fun getfeed(): Call<List<FeedData>>

    @POST("register")
    fun postRegister(json: String): Boolean

    @GET("User")
    fun getUser(id : Long): User
    @PUT("updateUser")
    fun putUpdateUser(json: String): Boolean




}
