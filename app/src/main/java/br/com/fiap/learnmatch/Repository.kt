package br.com.fiap.learnmatch

import android.content.Context
import com.google.gson.Gson
import org.json.JSONArray
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileWriter

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
    fun addJsonToFile(json: String): Boolean {
        try {
            // Converter o JSON para objeto UserData
            val userData = Gson().fromJson(json, UserData::class.java)

            // Determinar o nome do arquivo com base no tipo
            val nomeArquivo = when (userData.type) {
                "Mentor" -> "Mentor.json"
                "Student" -> "Student.json"
                else -> throw IllegalArgumentException("Tipo inválido: ${userData.type}")
            }

            val arquivo = File(context.filesDir, nomeArquivo)

            // Adicionar o JSON ao arquivo
            FileWriter(arquivo, true).use { it.write("$json\n") }


            return true // Sucesso: o JSON foi adicionado ou o arquivo foi criado
        } catch (e: Exception) {
            e.printStackTrace()
            return false // Falha: algo deu errado ao adicionar o JSON ou criar o arquivo
        }
    }

    public fun loadJsonFromFile(context: Context, fileName: String): List<UserData> {
        val file = File(context.filesDir, fileName)
        val userDataList = mutableListOf<UserData>()

        if (file.exists()) {
            try {
                val jsonString = file.readText()
                val jsonArray = JSONArray(jsonString)

                for (i in 0 until jsonArray.length()) {
                    val json = jsonArray.getJSONObject(i).toString()
                    val userData = Gson().fromJson(json, UserData::class.java)
                    userDataList.add(userData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return userDataList
    }



}