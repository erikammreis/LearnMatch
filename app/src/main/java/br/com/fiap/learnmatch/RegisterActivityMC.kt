package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class RegisterActivityMC : AppCompatActivity() {
    private lateinit var spinnerExperienceTimeMcRegister: Spinner
    private lateinit var spinnerFieldOfWorkMcRegister: Spinner
    private lateinit var registerMcOffice: EditText
    private lateinit var registerMcExperience: EditText
    private lateinit var registerMCBack: Button
    private lateinit var registerMcNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_m_c_mentor)
        initializeViews()

        registerMCBack.setOnClickListener {
            finish()
        }
        registerMcNext.setOnClickListener {
            saveRegisterInfo()
            val repository = Repository(this)
            val response = repository.addJsonToFile(UserInfo.salveAndGetJson(this))
            Log.i("@erika", "Mentor:repository.addJsonToFile(UserInfo.toJson()):" + response)
            val intent = Intent(this@RegisterActivityMC, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeViews() {
        spinnerExperienceTimeMcRegister = findViewById(R.id.spinnerExperienceTimeMcRegister)
        spinnerFieldOfWorkMcRegister = findViewById(R.id.spinnerFieldOfWorkMcRegister)
        registerMcOffice = findViewById(R.id.registerMcOffice)
        registerMcExperience = findViewById(R.id.registerMcExperience)
        registerMCBack = findViewById(R.id.registerMCBack)
        registerMcNext = findViewById(R.id.registerMcNext)
    }
    private fun saveRegisterInfo() {
        UserInfo.office = registerMcOffice.text.toString()
        UserInfo.experience = registerMcExperience.text.toString()
        if (spinnerFieldOfWorkMcRegister.selectedItem.toString() != getString(R.string.occupation_area_hint)) {
            UserInfo.occupationArea = spinnerFieldOfWorkMcRegister.selectedItem.toString()
        }
        if (spinnerFieldOfWorkMcRegister.selectedItem.toString() != getString(R.string.work_experience_hint)) {
            UserInfo.operatingTime = spinnerExperienceTimeMcRegister.selectedItem.toString()
        }
    }


}
