package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Date

class RegisterActivityDP : AppCompatActivity() {

    private lateinit var registerDpName: EditText
    private lateinit var registerDpCpf: EditText
    private lateinit var registerDpStreet: EditText
    private lateinit var registerDpCep: EditText
    private lateinit var registerDpCity: EditText
    private lateinit var editTextDate: EditText
    private lateinit var registerDpBack: Button
    private lateinit var registerDpNext: Button
    private lateinit var stateSpinnerDpRegister: Spinner
    private lateinit var stateSpinnerDpSexo: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_dp)
        initializeViews()

        editTextDate.addTextChangedListener(object : TextWatcher {
            private var currentLength = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                currentLength = s?.length ?: 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == 2 && currentLength < s.length) {
                    val day = s.toString()
                    if (day.toInt() <= 31) {
                        editTextDate.setText(String.format("%s/", s.toString()))
                        editTextDate.setSelection(editTextDate.text.length)
                    }
                } else if (s != null && s.length == 5 && currentLength < s.length) {
                    val month = s.substring(3, 5)
                    if (month.toInt() <= 12) {
                        editTextDate.setText(String.format("%s/", s.toString()))
                        editTextDate.setSelection(editTextDate.text.length)
                    }
                }
            }
        })

        registerDpBack.setOnClickListener {
            finish()
        }
        registerDpNext.setOnClickListener {
            if (validateFields()) {
                saveRegisterInfo()
                val intent = Intent(this@RegisterActivityDP, RegisterActivityEI::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initializeViews() {
        registerDpBack = findViewById(R.id.registerDpBack)
        registerDpNext = findViewById(R.id.registerDpNext)
        registerDpName = findViewById(R.id.registerDpName)
        registerDpCpf = findViewById(R.id.registerDpCpf)
        registerDpStreet = findViewById(R.id.registerDpStreet)
        registerDpCep = findViewById(R.id.registerDpCep)
        registerDpCity = findViewById(R.id.registerDpCity)
        editTextDate = findViewById(R.id.editTextDate)
        stateSpinnerDpRegister = findViewById(R.id.stateSpinnerDpRegister)
        stateSpinnerDpSexo = findViewById(R.id.stateSpinnerDpSexo)

    }

    private fun saveRegisterInfo() {
        UserInfo.name = registerDpName.text.toString()
        UserInfo.cpf = registerDpCpf.text.toString()
        UserInfo.street = registerDpStreet.text.toString()
        UserInfo.cep = registerDpCep.text.toString()
        UserInfo.city = registerDpCity.text.toString()
        UserInfo.dateOfBirth = editTextDate.text.toString()
        UserInfo.state = stateSpinnerDpRegister.selectedItem.toString()
        UserInfo.sex = stateSpinnerDpSexo.selectedItem.toString()
    }



    private fun validateFields(): Boolean {
        val name = registerDpName.text.toString()
        val cpf = registerDpCpf.text.toString()
        val street = registerDpStreet.text.toString()
        val cep = registerDpCep.text.toString()
        val dateString = editTextDate.text.toString()
        val cityDpRegister = registerDpCity.text.toString()
        val stateDpRegister = stateSpinnerDpRegister.selectedItem.toString()
        val stateDpSexo = stateSpinnerDpSexo.selectedItem.toString()
        var errorsCount = 0
        if (name.isEmpty() || cpf.isEmpty() || street.isEmpty() || cep.isEmpty() || dateString.isEmpty()) {
            Toast.makeText(this, getString(R.string.Field_needs_to_be_filled_in), Toast.LENGTH_SHORT).show()
            errorsCount++
            if(name.isEmpty()){
                registerDpName.error = getString(R.string.Field_needs_to_be_filled_in)
            }
            if (cpf.isEmpty()){
                registerDpCpf.error = getString(R.string.Field_needs_to_be_filled_in)
            }
            if(street.isEmpty()){
                registerDpStreet.error = getString(R.string.Field_needs_to_be_filled_in)
            }
            if (cep.isEmpty()){
                registerDpCep.error = getString(R.string.Field_needs_to_be_filled_in)
            }
            if(dateString.isEmpty()){
                editTextDate.error = getString(R.string.Field_needs_to_be_filled_in)
            }
            if(cityDpRegister.isEmpty()){
                registerDpCity.error = getString(R.string.Field_needs_to_be_filled_in)
            }
        }

        if (cpf.length != 11) {
            registerDpCpf.error = getString(R.string.cpf_invalid)
            errorsCount++
        }

        if (cep.length != 8) {
            registerDpCep.error = getString(R.string.cep_invalid)
            errorsCount++
        }

        val date: Date? = StaticMethods.parseDate(dateString)

        if (date == null || !isOver18YearsOld(date) || StaticMethods.isDateValid(date)) {
            editTextDate.error = getString(R.string.date_invalid)
            errorsCount++
        }

        if (stateDpRegister == getString(R.string.select_state) || stateDpSexo == getString(R.string.select_sex)) {
            Toast.makeText(this, getString(R.string.select_state_gender_error), Toast.LENGTH_SHORT)
                .show()
            errorsCount++
        }
        if (errorsCount == 0) {
            return true
        } else {
            return false
        }
    }


    private fun isOver18YearsOld(editTextDate: Date): Boolean {
        val currentDate = Calendar.getInstance()

        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = editTextDate

        var age = currentDate.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
        if (currentDate.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        UserInfo.age = age
        return age >= 18
    }

}
