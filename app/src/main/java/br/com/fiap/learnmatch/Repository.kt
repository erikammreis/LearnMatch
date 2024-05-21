package br.com.fiap.learnmatch

import android.content.Context
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
    fun getFeed(callback: Callback<List<FeedData>>) {
        val call = apiService.getfeed()
        call.enqueue(callback)
    }
    fun getMentorsFromApi(callback: Callback<List<UserData>>) {
        val call = apiService.getMentors()
        call.enqueue(callback)
    }
    fun registerUser(json: String){
//        apiService.postRegister(json)
       //Methodo para cadastrar o usuario
    }
    fun getUser(id:Long): User{
        return apiService.getUser(id)
    }

    fun ValidateLogin(email : String , Password : String) : Boolean{
//        pegar getUser()

        return true
        //Methodo validar o login, escreve no arquivo interno para trabalhar com ele usando os methodos do UserInfo
    }

    fun UpdateUser(json : String ) : Boolean{
//        apiService.putUpdateUser(json)
        return true
    }




}