package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentActivity : AppCompatActivity() {
    private lateinit var assessmentNameMentorStudent: TextView
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var start1Assess: ImageButton
    private lateinit var start2Assess: ImageButton
    private lateinit var start3Assess: ImageButton
    private lateinit var start4Assess: ImageButton
    private lateinit var start5Assess: ImageButton
    private lateinit var imageView20: ImageView
    private lateinit var buttonSend: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        initializeViews()

        var userData: UserData? =  null
        val user = UserInfo.getUserInf(this)
        var repository = Repository(this)
        if (user.type.equals("Mentor")) {
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                imageView20.setImageResource(R.drawable.icon_black_teach)
                if (response.isSuccessful) {
                    var userDataLists = response.body() ?: emptyList()
                    for (userDataList in userDataLists) {
                        if(userDataList.id == StaticIndex.idUserDatar?.toLong()) {
                            assessmentNameMentorStudent.text = userDataList!!.name
                            userData = userDataList
                        }
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })
        } else if(user.type.equals("Student")){
            imageView20.setImageResource(R.drawable.icon_black_learn)
            repository.getMentorsFromApi(object : Callback<List<UserData>> {
                override fun onResponse(
                    call: Call<List<UserData>>,
                    response: Response<List<UserData>>
                ) {
                    imageView20.setImageResource(R.drawable.icon_black_teach)
                    if (response.isSuccessful) {
                        var userDataLists = response.body() ?: emptyList()
                        for (userDataList in userDataLists) {
                            if(userDataList.id == StaticIndex.idUserDatar?.toLong()) {
                                assessmentNameMentorStudent.text = userDataList!!.name
                                userData = userDataList
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
            val intent = Intent(this@AssessmentActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@AssessmentActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
        var note = 0
        start1Assess.setOnClickListener {
            start1Assess.setImageResource(R.drawable.baseline_star_24_white)
            start2Assess.setImageResource(R.drawable.baseline_star_border_24)
            start3Assess.setImageResource(R.drawable.baseline_star_border_24)
            start4Assess.setImageResource(R.drawable.baseline_star_border_24)
            start5Assess.setImageResource(R.drawable.baseline_star_border_24)
            note = 2
        }
        start2Assess.setOnClickListener {
            start1Assess.setImageResource(R.drawable.baseline_star_24_white)
            start2Assess.setImageResource(R.drawable.baseline_star_24_white)
            start3Assess.setImageResource(R.drawable.baseline_star_border_24)
            start4Assess.setImageResource(R.drawable.baseline_star_border_24)
            start5Assess.setImageResource(R.drawable.baseline_star_border_24)
            note = 4
        }
        start3Assess.setOnClickListener {
            start1Assess.setImageResource(R.drawable.baseline_star_24_white)
            start2Assess.setImageResource(R.drawable.baseline_star_24_white)
            start3Assess.setImageResource(R.drawable.baseline_star_24_white)
            start4Assess.setImageResource(R.drawable.baseline_star_border_24)
            start5Assess.setImageResource(R.drawable.baseline_star_border_24)
            note = 6
        }
        start4Assess.setOnClickListener {
            start1Assess.setImageResource(R.drawable.baseline_star_24_white)
            start2Assess.setImageResource(R.drawable.baseline_star_24_white)
            start3Assess.setImageResource(R.drawable.baseline_star_24_white)
            start4Assess.setImageResource(R.drawable.baseline_star_24_white)
            start5Assess.setImageResource(R.drawable.baseline_star_border_24)
            note = 8
        }
        start5Assess.setOnClickListener {
            start1Assess.setImageResource(R.drawable.baseline_star_24_white)
            start2Assess.setImageResource(R.drawable.baseline_star_24_white)
            start3Assess.setImageResource(R.drawable.baseline_star_24_white)
            start4Assess.setImageResource(R.drawable.baseline_star_24_white)
            start5Assess.setImageResource(R.drawable.baseline_star_24_white)
            note = 10
        }
        buttonSend.setOnClickListener {
            addNote(userData!!,note)
            removeMatch(userData!!)
            val intent = Intent(this@AssessmentActivity, ChatsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun initializeViews() {
        imageView20 = findViewById(R.id.imageView20)
        assessmentNameMentorStudent = findViewById(R.id.assessmentNameMentorStudent)
        start1Assess = findViewById(R.id.start1Assess)
        start2Assess = findViewById(R.id.start2Assess)
        start3Assess = findViewById(R.id.start3Assess)
        start4Assess = findViewById(R.id.start4Assess)
        start5Assess = findViewById(R.id.start5Assess)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        buttonSend = findViewById(R.id.buttonSend)
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
    private fun addNote(userData: UserData,newnote : Int) {
        val repository = Repository(this)
        val note = userData.evaluationNote
        var newNote = (note!! + newnote)/2
        userData.evaluationNote = newNote

        repository.UpdateUser(StaticMethods.getJsonUserDate(this, userData))
    }
}