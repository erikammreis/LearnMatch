package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerNext = findViewById(R.id.registerNext)

        registerNext.setOnClickListener {
            // Crie uma intenção para iniciar a CadastroActivity
            val intent = Intent(this@RegisterActivity, RegisterActivityDP::class.java)
            startActivity(intent)
        }
    }
}
