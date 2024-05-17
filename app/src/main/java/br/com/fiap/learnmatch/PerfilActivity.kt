package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {
    private lateinit var perfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var buttonPerfilEdit: ImageButton
    private lateinit var buttonPerfilFeed: ImageButton
    private lateinit var buttonPerfilSettings: ImageButton
    private lateinit var notificaButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        initializeViews()

        notificaButton.setOnClickListener {
            val intent = Intent(this@PerfilActivity, NotificationActivity::class.java)
            startActivity(intent)
        }

        buttonPerfilEdit.setOnClickListener {
            val intent = Intent(this@PerfilActivity, EditPerfilActivity::class.java)
            startActivity(intent)
        }
        buttonPerfilFeed.setOnClickListener {
            val intent = Intent(this@PerfilActivity, FeedActivity::class.java)
            startActivity(intent)
        }
        buttonPerfilSettings.setOnClickListener {
            val intent = Intent(this@PerfilActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        homeButtonMenu.setOnClickListener{
            val intent = Intent(this@PerfilActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }

        perfilButtonMenu.setOnClickListener {
            val intent = Intent(this@PerfilActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@PerfilActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeViews() {
        perfilButtonMenu = findViewById(R.id.perfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        buttonPerfilEdit = findViewById(R.id.buttonPerfilEdit)
        buttonPerfilFeed = findViewById(R.id.buttonPerfilFeed)
        buttonPerfilSettings = findViewById(R.id.buttonPerfilSettings)
        notificaButton = findViewById(R.id.notificaButton)
    }
}