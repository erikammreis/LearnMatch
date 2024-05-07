package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivityEP : AppCompatActivity() {

    private lateinit var registerEpNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_e_p_student)

        registerEpNext = findViewById(R.id.registerEpNext)

        registerEpNext.setOnClickListener {
            // Crie uma intenção para iniciar a CadastroActivity
            val intent = Intent(this@RegisterActivityEP, RegisterActivityEI::class.java)
            startActivity(intent)
        }
    }
}
