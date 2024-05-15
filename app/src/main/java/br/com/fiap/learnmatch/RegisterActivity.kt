package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var buttonTeach: ImageButton
    private lateinit var buttonLearn: ImageButton
    private lateinit var registerback: Button
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeViews()

        buttonTeach.setOnClickListener {
//            if (validateFields()) {
                saveRegisterInfo("Mentor")
                val intent = Intent(this@RegisterActivity, RegisterActivityDP::class.java)
                startActivity(intent)
//            }
        }
        buttonLearn.setOnClickListener {
//            if (validateFields()) {
                saveRegisterInfo("Student")
                val intent = Intent(this@RegisterActivity, RegisterActivityDP::class.java)
                startActivity(intent)
//            }
        }
        registerback.setOnClickListener {
            finish()
        }
    }

    private fun initializeViews() {
        registerback = findViewById(R.id.registerback)
        registerEmail = findViewById(R.id.registerEmail)
        registerPassword = findViewById(R.id.registerPassword)
        buttonTeach = findViewById(R.id.buttonImagTeach)
        buttonLearn = findViewById(R.id.buttonImagLearn)
    }

    private fun validateFields(): Boolean {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()

        if((email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) && (password.isEmpty())) {
            registerEmail.error = getString(R.string.invalid_email)
            registerPassword.error = getString(R.string.enter_password)
            return false
        }else if(((email.isNotEmpty() && password.length < 8) && (email.isEmpty() ||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()))) {
                registerEmail.error = getString(R.string.invalid_email)
                registerPassword.error = getString(R.string.password_length)
                return false
        }else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.error = getString(R.string.invalid_email)
            return false
        }else if (password.isEmpty()) {
            registerPassword.error = getString(R.string.enter_password)
            return false
        }else if(password.length < 8){
            registerPassword.error = getString(R.string.password_length)
            return false
        }

        return true
    }

    private fun saveRegisterInfo(type: String) {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        UserInfo.email = email
        UserInfo.password = password
        UserInfo.type = type
    }
}
