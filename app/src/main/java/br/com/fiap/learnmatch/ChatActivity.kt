package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {
    private lateinit var nameChat: TextView
    private lateinit var chatMentorStudent: TextView
    private lateinit var chatTeachLearn: TextView
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var closeChat: ImageButton
    private lateinit var buttonAssessment: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initializeViews()

        var userData: UserData? = null
        var repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        if (user.type.equals("Mentor")) {
            repository.getStudentsFromApi(object : Callback<List<UserData>> {
                override fun onResponse(
                    call: Call<List<UserData>>,
                    response: Response<List<UserData>>
                ) {
                    if (response.isSuccessful) {
                        var userDataLists = response.body() ?: emptyList()
                        for (userDataList in userDataLists) {
                            if (userDataList.id == StaticIndex.idUserDatar?.toLong()) {
                                userData = userDataList
                                displayUserData(userDataList)
                            }
                        }
                    } else {
                    }
                }

                override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                }
            })
        } else if (user.type.equals("Student")) {
            repository.getMentorsFromApi(object : Callback<List<UserData>> {
                override fun onResponse(
                    call: Call<List<UserData>>,
                    response: Response<List<UserData>>
                ) {
                    if (response.isSuccessful) {
                        var userDataLists = response.body() ?: emptyList()
                        for (userDataList in userDataLists) {
                            if (userDataList.id == StaticIndex.idUserDatar?.toLong()) {
                                userData = userDataList
                                displayUserData(userDataList)
                            }
                        }
                    } else {
                    }
                }

                override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                }
            })
        }


        closeChat.setOnClickListener {
            showCloseMatchDialog(userData!!)
        }
        buttonAssessment.setOnClickListener {
            StaticIndex.idUserDatar = userData!!.id.toInt()
            val intent = Intent(this@ChatActivity, AssessmentActivity::class.java)
            startActivity(intent)
        }
        homeButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun removeMatch(userData: UserData) {
        val repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        val match = userData.match?.toMutableList() ?: mutableListOf()
        match.remove(user.id)
        userData.match = match.toTypedArray()

        repository.UpdateUser(StaticMethods.getJsonUserDate(this, userData))

        val matchUser = user.match?.toMutableList() ?: mutableListOf()
        matchUser.remove(user.id)
        user.match = matchUser.toTypedArray()

        repository.UpdateUser(StaticMethods.getJsonUser(this, user))
    }

    fun showCloseMatchDialog(userData: UserData) {
        val builder = AlertDialog.Builder(this@ChatActivity)

        builder.setTitle("Encerrar Match")
        builder.setMessage("Você deseja encerrar o match?")

        builder.setPositiveButton("Sim") { dialog, which ->
            val intent = Intent(this@ChatActivity, ChatsActivity::class.java)
            startActivity(intent)
            removeMatch(userData)
            finish()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun initializeViews() {
        buttonAssessment = findViewById(R.id.buttonAssessment)
        closeChat = findViewById(R.id.closeChat)
        nameChat = findViewById(R.id.nameChat)
        chatTeachLearn = findViewById(R.id.chatTeachLearn)
        chatMentorStudent = findViewById(R.id.chatMentorStudent)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)

    }

    private fun displayUserData(userData: UserData) {
        nameChat.text = userData.name
        chatMentorStudent.text = userData.name

        if (userData.type.equals("Student")) {
            chatTeachLearn.text = "Ensinar"
        }
        if (userData.type.equals("Mentor")) {
            chatTeachLearn.text = "Aprender"
        }


    }
}