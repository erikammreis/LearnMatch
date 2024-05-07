package br.com.fiap.learnmatch


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivityDP : AppCompatActivity() {

    private lateinit var registerDpNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_dp)

        registerDpNext = findViewById(R.id.registerDpNext)

        registerDpNext.setOnClickListener {
            // Crie uma intenção para iniciar a CadastroActivity
            val intent = Intent(this@RegisterActivityDP, RegisterActivityEP::class.java)
            startActivity(intent)
        }
    }
}
