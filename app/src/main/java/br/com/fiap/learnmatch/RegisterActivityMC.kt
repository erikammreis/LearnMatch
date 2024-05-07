package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivityMC : AppCompatActivity() {

    private lateinit var registerMcNext: Button
    private lateinit var buttonSundayMc: Button
    private lateinit var buttonMondayMC: Button
    private lateinit var buttonTuesdayMc: Button
    private lateinit var buttonWednesdayMc: Button
    private lateinit var buttonThursdayMc: Button
    private lateinit var buttonFridayMc: Button
    private lateinit var buttonSaturdayMc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_m_c_mentor)
        setupViews()
        registerMcNext.setOnClickListener {
            // Criar uma intenção para iniciar a CadastroActivity
            val intent = Intent(this@RegisterActivityMC, RegisterActivityAi::class.java)
            startActivity(intent)
        }
    }

    private fun setupViews() {
        registerMcNext = findViewById(R.id.registerMcNext)
        buttonSundayMc = findViewById(R.id.buttonSundayMc)
        buttonMondayMC = findViewById(R.id.buttonMondayMC)
        buttonTuesdayMc = findViewById(R.id.buttonTuesdayMc)
        buttonWednesdayMc = findViewById(R.id.buttonWednesdayMc)
        buttonThursdayMc = findViewById(R.id.buttonThursdayMc)
        buttonFridayMc = findViewById(R.id.buttonFridayMc)
        buttonSaturdayMc = findViewById(R.id.buttonSaturdayMc)
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val selectedColor: Int
        val textColor: Int

        if (button.tag == null || button.tag as Int == 0) {
            // Botão não selecionado, definir as cores de selecionado
            selectedColor = resources.getColor(R.color.orange)
            textColor = resources.getColor(R.color.white)
            button.tag = 1
        } else {
            selectedColor = resources.getColor(R.color.gray)
            textColor = resources.getColor(R.color.orange)
            button.tag = 0
        }

        button.setBackgroundColor(selectedColor)
        button.setTextColor(textColor)
    }
}
