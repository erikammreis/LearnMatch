package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchScreenActivity : AppCompatActivity() {
    private lateinit var nameUserData: TextView
    private lateinit var learnTeach: TextView
    private lateinit var sheHe: TextView
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var ButtonChat: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen)
        initializeViews()
        var repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        var userDataListId: Int? = null
        if(user.type.equals("Mentor")){
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    var userDataLists = response.body() ?: emptyList()
                    for (userData in userDataLists) {
                        if (userData.id == StaticIndex.idUserDatar?.toLong()) {
                            displayUserData(userData)
                            userDataListId = userData.id.toInt()
                        }
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })
        }else if(user.type.equals("Student")){
            repository.getMentorsFromApi(object : Callback<List<UserData>> {
                override fun onResponse(
                    call: Call<List<UserData>>,
                    response: Response<List<UserData>>
                ) {
                    if (response.isSuccessful) {
                        var userDataLists = response.body() ?: emptyList()
                        for (userData in userDataLists) {
                            if (userData.id == StaticIndex.idUserDatar?.toLong()) {
                                displayUserData(userData)
                                userDataListId = userData.id.toInt()
                            }
                        }
                    } else {
                    }
                }

                override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                }
            })
        }
        homeButtonMenu.setOnClickListener {
            if(user.type.equals("Mentor")) {
                val intent = Intent(this@MatchScreenActivity, MatchScreenMentorActivity::class.java)
                startActivity(intent)
            }else if(user.type.equals("Student")){
                val intent = Intent(this@MatchScreenActivity, MatchScreenStudentActivity::class.java)
                startActivity(intent)
            }
        }
        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
        ButtonChat.setOnClickListener {
            StaticIndex.idUserDatar = userDataListId

            val intent = Intent(this@MatchScreenActivity, ChatActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun initializeViews() {
        nameUserData = findViewById(R.id.nameUserData)
        learnTeach = findViewById(R.id.learnTeach)
        sheHe = findViewById(R.id.sheHe)
        ButtonChat = findViewById(R.id.ButtonChat)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)

    }

    private fun displayUserData(userData: UserData) {
        nameUserData.text = userData.name
        if (userData.type.equals("Student")) {
            learnTeach.text = "ensinar:"
            if (userData.sex.equals("Feminino")) {
                sheHe.text = "ela"
            }
            if (userData.sex.equals("Masculino")) {
                sheHe.text = "ele"
            }
        }
        if (userData.type.equals("Mentor")) {
            learnTeach.text = "aprender com:"
            if (userData.sex.equals("Feminino")) {
                sheHe.text = "ela"
            }
            if (userData.sex.equals("Masculino")) {
                sheHe.text = "ele"
            }
        }


    }

}