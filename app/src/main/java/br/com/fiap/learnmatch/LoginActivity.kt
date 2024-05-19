package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var saveLoginCheckBox: CheckBox
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeViews()
        loadSavedLoginInfo()
        val user = UserInfo.getUserInf(this)
        loginButton.setOnClickListener {
            saveLoginInfo()
            val repository = Repository(this)
            if (repository.ValidateLogin(emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )) {
                val intent = Intent(this@LoginActivity, getTypeUser())
                startActivity(intent)
            } else {
                Toast.makeText(this, "email ou senha inválidos", Toast.LENGTH_SHORT).show()

            }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }



    private fun initializeViews() {
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun loadSavedLoginInfo() {
        if (UserInfo.saveLoginCheckBox == true) {
            saveLoginCheckBox.isChecked = true
            emailEditText.setText(UserInfo.email)
            passwordEditText.setText(UserInfo.password)
        }
    }

    private fun getTypeUser(): Class<*> {
        val user = UserInfo.getUserInf(this)
        return if (user.type == "Student") {
            MatchScreenStudentActivity::class.java
        } else {
            MatchScreenMentorActivity::class.java
        }
    }

    private fun saveLoginInfo() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (saveLoginCheckBox.isChecked) {
            UserInfo.email = email
            UserInfo.password = password
            UserInfo.saveLoginCheckBox = true
        }
    }
}
