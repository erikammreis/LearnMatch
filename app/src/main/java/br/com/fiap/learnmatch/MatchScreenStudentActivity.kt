package br.com.fiap.learnmatch

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MatchScreenStudentActivity : AppCompatActivity() {
    private lateinit var nameUser: TextView
    private lateinit var courseStudentMatch: TextView
    private lateinit var buttonMatchMentor: ImageButton
    private lateinit var noMatchMentor: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_student)
        initializeViews()

    }
    private fun initializeViews() {
        nameUser = findViewById(R.id.nameUser)
        courseStudentMatch = findViewById(R.id.courseStudentMatch)
        buttonMatchMentor = findViewById(R.id.buttonMatchMentor)
        noMatchMentor = findViewById(R.id.noMatchMentor)
    }
}