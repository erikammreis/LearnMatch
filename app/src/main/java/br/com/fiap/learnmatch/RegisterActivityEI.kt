package br.com.fiap.learnmatch

import UserInfo
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Date


class RegisterActivityEI : AppCompatActivity() {


    private var searchText: String? = null
    private var chipTexts = mutableListOf<String>()
    private lateinit var chipGroup: ChipGroup
    private lateinit var allInterests: Array<String>
    private lateinit var registerEINext: Button
    private lateinit var registerEiBack: Button
    private lateinit var textViewInterestSkill: TextView
    private lateinit var registerEiEducationalInstitution: EditText
    private lateinit var editTextSearch: EditText
    private lateinit var registerEiCourse: EditText
    private lateinit var registerEiDateStartEdit: EditText
    private lateinit var spinnerTypeTeachingEiRegister: Spinner
    private lateinit var spinnerDurationEiRegister: Spinner

    private fun setSearchText(searchText: String) {
        this.searchText = searchText
    }

    private fun getSearchText(): String? {
        return searchText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_e_i)
        modifyingView()
        initializeViews()


        registerEINext.setOnClickListener {
            if(validateFields()){
                saveRegisterInfo()
                if(UserInfo.type.equals("Student")){
                    val intent = Intent(this@RegisterActivityEI, MatchScreenStudentActivity::class.java)
                    startActivity(intent)
                }else if (UserInfo.type.equals("Mentor")) {
                    val intent = Intent(this@RegisterActivityEI, RegisterActivityMC::class.java)
                    startActivity(intent)
                }
            }
        }
        registerEiBack.setOnClickListener {
            finish()
        }

        val searchEditText = findViewById<EditText>(R.id.editTextSearch)
        val addChipButton = findViewById<Button>(R.id.button_add_chip)
        chipTexts = mutableListOf()

        registerEiDateStartEdit.addTextChangedListener(object : TextWatcher {
            private var currentLength = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                currentLength = s?.length ?: 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == 2 && currentLength < s.length) {
                    val day = s.toString()
                    if (day.toInt() <= 31) {
                        registerEiDateStartEdit.setText(String.format("%s/", s.toString()))
                        registerEiDateStartEdit.setSelection(registerEiDateStartEdit.text.length)
                    }
                } else if (s != null && s.length == 5 && currentLength < s.length) {
                    val month = s.substring(3, 5)
                    if (month.toInt() <= 12) {
                        registerEiDateStartEdit.setText(String.format("%s/", s.toString()))
                        registerEiDateStartEdit.setSelection(registerEiDateStartEdit.text.length)
                    }
                }
            }
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setSearchText(s.toString())
                showSuggestions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        addChipButton.setOnClickListener {
            if (shouldAddChip()) {
                addChip(getSearchText())
            }
            clearSearchText()
        }
    }

    private fun initializeViews() {
        allInterests = resources.getStringArray(R.array.interest)
        chipGroup = findViewById(R.id.chipGroup)
        editTextSearch = findViewById(R.id.editTextSearch)
        registerEiBack = findViewById(R.id.registerEiBack)
        registerEINext = findViewById(R.id.registerEiNext)
        registerEiEducationalInstitution = findViewById(R.id.registerEiEducationalInstitution)
        registerEiCourse = findViewById(R.id.registerEiCourse)
        registerEiDateStartEdit = findViewById(R.id.registerEiDateStartEdit)
        spinnerTypeTeachingEiRegister = findViewById(R.id.spinnerTypeTeachingEiRegister)
        spinnerDurationEiRegister = findViewById(R.id.spinnerDurationEiRegister)

    }

    private fun saveRegisterInfo() {
        UserInfo.educationalInstitution = registerEiEducationalInstitution.text.toString()
        UserInfo.durationCourse = spinnerDurationEiRegister.selectedItem.toString()
        UserInfo.course = registerEiCourse.text.toString()
        UserInfo.dateStart = StaticMethods.parseDate(registerEiDateStartEdit.text.toString())
        UserInfo.typeTeaching = spinnerTypeTeachingEiRegister.selectedItem.toString()
        UserInfo.interest = chipTexts.toTypedArray()
    }

    private fun modifyingView() {
        textViewInterestSkill = findViewById(R.id.textViewInterestSkill)
        if (UserInfo.type.equals("Student")) {
            textViewInterestSkill.setText(getString(R.string.interests_hint))
        } else if (UserInfo.type.equals("Mentor")) {
            textViewInterestSkill.setText(getString(R.string.skills_hint_view))
        }
    }

    private fun validateFields(): Boolean {
        val educationalInstitution = registerEiEducationalInstitution.text.toString()
        val course = registerEiCourse.text.toString()
        val dateStart = registerEiDateStartEdit.text.toString()
        val typeTeaching = spinnerTypeTeachingEiRegister.selectedItem.toString()
        val durationCourse = spinnerDurationEiRegister.selectedItem.toString()
        val cityDpRegister = chipTexts.toTypedArray()

        var errorsCount = 0
        if (educationalInstitution.isEmpty() || course.isEmpty() || dateStart.isEmpty() || cityDpRegister.isEmpty()) {
            Toast.makeText(this, getString(R.string.Field_needs_to_be_filled_in), Toast.LENGTH_SHORT).show()
            errorsCount++
            if (educationalInstitution.isEmpty()) {
                registerEiEducationalInstitution.error =
                    getString(R.string.Field_needs_to_be_filled_in)
                errorsCount++
            }
            if (course.isEmpty()) {
                registerEiCourse.error = getString(R.string.Field_needs_to_be_filled_in)
                errorsCount++
            }
            if (dateStart.isEmpty()) {
                registerEiDateStartEdit.error = getString(R.string.Field_needs_to_be_filled_in)
                errorsCount++
            }
            if (cityDpRegister.isEmpty()) {
                if(UserInfo.type.equals("Student")){
                    editTextSearch.error = getString(R.string.add_interest_error_student)
                }else if(UserInfo.type.equals("Mentor")) {
                    editTextSearch.error = getString(R.string.add_interest_error_mentor)
                }
                errorsCount++
            }
        }

        val date: Date? = StaticMethods.parseDate(dateStart)
        if (date == null || StaticMethods.isDateValid(date)) {
            registerEiDateStartEdit.error = getString(R.string.date_invalid_start)
            errorsCount++
        }

        if (typeTeaching == getString(R.string.education_type_select) || durationCourse == getString(R.string.duration_select)) {
            Toast.makeText(
                this,
                getString(R.string.select_type_duration_error),
                Toast.LENGTH_SHORT
            )
                .show()
            errorsCount++
        }
        if (errorsCount == 0) {
            return true
        } else {
            return false
        }
    }


    private fun shouldAddChip(): Boolean {
        val searchText = getSearchText()
        if (searchText != null && searchText.length >= 3) {
            var alreadyExists = false
            for (chipText in chipTexts) {
                if (chipText.equals(searchText, ignoreCase = true)) {
                    alreadyExists = true
                    break
                }
            }
            if (!alreadyExists) {
                return true
            } else {
                showMessage("Esse interesse já está adicionado.")
                return false
            }
        }
        return false
    }

    private fun clearSearchText() {
        val searchEditText = findViewById<EditText>(R.id.editTextSearch)
        searchEditText.setText("")
    }

    private fun showSuggestions(searchText: String) {
        if (searchText.length >= 4) {
            val suggestedInterests = mutableListOf<String>()
            for (interest in allInterests) {
                if (interest.toLowerCase()
                        .contains(searchText.toLowerCase()) && !chipTexts.contains(interest)
                ) {
                    suggestedInterests.add(interest)
                }
            }
            if (suggestedInterests.isNotEmpty()) {
                showSelectionDialog(suggestedInterests.toTypedArray())
            }
        }
    }

    private fun showSelectionDialog(suggestions: Array<String>) {
        val builder = AlertDialog.Builder(this)
        if (suggestions.isNotEmpty()) {
            builder.setItems(suggestions) { dialog, which ->
                val selectedInterest = suggestions[which]
                if (!chipTexts.contains(selectedInterest)) {
                    addChip(selectedInterest)
                    clearSearchText()
                } else {
                    showMessage("Esse interesse já está adicionado.")
                }
            }

            builder.setNegativeButton("Fechar", null)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setGravity(Gravity.BOTTOM)
        }
    }

    private fun addChip(text: String?) {
        text?.let {
            val chip = Chip(this)
            chip.text = text
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(it)
                chipTexts.remove((it as Chip).text.toString())
            }
            if (!chipTexts.contains(text)) {
                chipGroup.addView(chip)
                chipTexts.add(text)
            }
        }
    }

    private fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun addSlashToDateText(dateText: String): String {
        val formattedText = StringBuilder(dateText)
        if (dateText.length == 2) {
            formattedText.append("/")
        } else if (dateText.length == 5) {
            formattedText.append("/")
        }
        return formattedText.toString()
    }


}
