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

    fun getMentorsFromApi(callback: Callback<List<UserData>>) {
        val call = apiService.getMentors()
        call.enqueue(callback)
    }
    fun addJsonToFile(json: String){
       //Methodo para cadastrar o usuario
    }

    public  fun ValidateLogin(email : String , Password : String) : Boolean{
        return true
        //Methodo validar o login
    }

    public  fun UpdateUser(json : String ) : Boolean{
        //atulizar usuario
        return true
    }


}