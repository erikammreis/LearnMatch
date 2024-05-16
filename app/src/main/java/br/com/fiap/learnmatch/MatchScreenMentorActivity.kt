package br.com.fiap.learnmatch
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchScreenMentorActivity : AppCompatActivity() {
    private lateinit var nameUser: TextView
    private lateinit var courseStudentMatch: TextView
    private lateinit var buttonMatchMentor: ImageButton
    private lateinit var noMatchMentor: ImageButton
    private var currentJsonIndex = 0
    private lateinit var studentList: List<UserData>
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_mentor)

        // Inicialize a Repository
        repository = Repository(this)

        // Carregue os dados dos alunos da API
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(call: Call<List<UserData>>, response: Response<List<UserData>>) {
                if (response.isSuccessful) {
                    studentList = response.body() ?: emptyList()
                    Log.i("@erika", "teste:" + studentList.size)
                    displayUserData(studentList[currentJsonIndex])
                } else {
                    // Lida com erros na resposta da API
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                // Lida com falhas na requisição
            }
        })

        initializeViews()
    }

    private fun initializeViews() {
        nameUser = findViewById(R.id.nameUser)
        courseStudentMatch = findViewById(R.id.courseStudentMatch)
        buttonMatchMentor = findViewById(R.id.buttonMatchMentor)
        noMatchMentor = findViewById(R.id.noMatchMentor)

        // Configura os listeners de clique para os botões
        buttonMatchMentor.setOnClickListener {
            if (currentJsonIndex < studentList.size - 1) {
                currentJsonIndex++
                displayUserData(studentList[currentJsonIndex])
            }
        }

        noMatchMentor.setOnClickListener {
            if (currentJsonIndex > 0) {
                currentJsonIndex--
                displayUserData(studentList[currentJsonIndex])
            }
        }
    }

    private fun displayUserData(userData: UserData) {
        nameUser.text = userData.name
        courseStudentMatch.text = userData.course
        // Outros campos podem ser exibidos da mesma maneira
    }
}
