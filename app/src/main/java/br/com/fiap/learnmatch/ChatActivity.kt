package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {
    private lateinit var nameUserData: TextView
    private lateinit var learnTeach: TextView
    private lateinit var sheHe: TextView
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var ButtonChat: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initializeViews()
        homeButtonMenu.setOnClickListener{
            val intent = Intent(this@ChatActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener{
            val intent = Intent(this@ChatActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener{
            val intent = Intent(this@ChatActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeViews() {
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)

    }
}