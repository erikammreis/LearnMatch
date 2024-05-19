package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
        homeButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
        ButtonChat.setOnClickListener{
            val intent = Intent(this@MatchScreenActivity, ChatActivity::class.java)
            startActivity(intent)
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

}