package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var buttonApply: Button
    private lateinit var buttonDefault: Button
    private lateinit var buttonSunday: Button
    private lateinit var buttonMonday: Button
    private lateinit var buttonTuesday: Button
    private lateinit var buttonWednesday: Button
    private lateinit var buttonThursday: Button
    private lateinit var buttonFriday: Button
    private lateinit var buttonSaturday: Button
    var period: MutableList<String> = mutableListOf()
    var dayOfTheWeek: MutableList<String> = mutableListOf()
    var settingsUser = SettingsUser()
    private lateinit var spinnerlocationRegister: Spinner
    private lateinit var settingsSpinnerSexo: Spinner
    private lateinit var settingsSpinnerFieldOfWork: Spinner
    private lateinit var morningCheckBox: CheckBox
    private lateinit var afternoonCheckBox: CheckBox
    private lateinit var nightCheckBox: CheckBox
    private lateinit var occupationArea: TextView
    private lateinit var typeUser: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupViews()
        setViewTypeUser()
        setViewStandard()
        visibilityView()

        buttonApply.setOnClickListener {
            savePeriod()
            setSettings()
            finish()
            val intent = Intent(this@SettingsActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)

        }
        buttonDefault.setOnClickListener {
            default()
            finish()
            val intent = Intent(this@SettingsActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }

    }
    fun setViewTypeUser(){
        val user = UserInfo.getUserInf(this)
     if(user.type.equals("Mentor")){
         typeUser.setText("Mentor")
     }else if(user.type.equals("Student")){
         typeUser.setText("Estudante")
     }
    }

    private fun setupViews() {
        buttonApply = findViewById(R.id.buttonApply)
        buttonDefault = findViewById(R.id.buttonDefault)
        buttonSunday = findViewById(R.id.buttonSunday)
        buttonMonday = findViewById(R.id.buttonMonday)
        buttonTuesday = findViewById(R.id.buttonTuesday)
        buttonWednesday = findViewById(R.id.buttonWednesday)
        buttonThursday = findViewById(R.id.buttonThursday)
        buttonFriday = findViewById(R.id.buttonFriday)
        buttonSaturday = findViewById(R.id.buttonSaturday)
        spinnerlocationRegister = findViewById(R.id.spinnerlocationRegister)
        settingsSpinnerSexo = findViewById(R.id.settingsSpinnerSexo)
        settingsSpinnerFieldOfWork = findViewById(R.id.settingsSpinnerFieldOfWork)
        morningCheckBox = findViewById(R.id.morningCheckBox)
        afternoonCheckBox = findViewById(R.id.afternoonCheckBox)
        nightCheckBox = findViewById(R.id.nightCheckBox)
        occupationArea = findViewById(R.id.occupationArea)
    }

    fun savePeriod() {
        if (morningCheckBox.isChecked) {
            period.add(morningCheckBox.text.toString())
        }
        if (afternoonCheckBox.isChecked) {
            period.add(afternoonCheckBox.text.toString())
        }
        if (nightCheckBox.isChecked) {
            period.add(nightCheckBox.text.toString())
        }
        sortPeriod(period)
    }

    fun sortPeriod(period: List<String>): List<String> {
        val order = listOf("Manhã", "Tarde", "Noite")
        return period.sortedWith(compareBy { order.indexOf(it) })
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val selectedColor: Int
        val textColor: Int

        if (button.tag == null || button.tag as Int == 0) {
            selectedColor = resources.getColor(R.color.orange)
            textColor = resources.getColor(R.color.white)
            button.tag = 1
            setValList(button)

        } else {
            selectedColor = resources.getColor(R.color.gray)
            textColor = resources.getColor(R.color.orange)
            button.tag = 0
            setValList(button)
        }

        button.setBackgroundColor(selectedColor)
        button.setTextColor(textColor)
    }

    fun setValList(button: Button) {
        val dayText = button.text.toString()

        if (dayOfTheWeek.contains(dayText)) {
            dayOfTheWeek.remove(dayText)
        } else {
            dayOfTheWeek.add(dayText)
        }
    }

    fun sortDaysOfWeek(dayOfTheWeek: List<String>): List<String> {
        val order = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
        return dayOfTheWeek.sortedWith(compareBy { order.indexOf(it) })
    }

    private fun visibilityView() {
        val user = UserInfo.getUserInf(this)
        if (user.type.equals("Mentor")) {
            occupationArea.visibility = View.GONE
            settingsSpinnerFieldOfWork.visibility = View.GONE
        }
    }

    fun default() {
        settingsUser.default()
        SettingsManager.setSettings(this, settingsUser)
        var teste = SettingsManager.getSettings(this)
        Log.i(
            "@erika", "period " + (teste.period?.joinToString()) +
                    "\ndayOfTheWeek  " + teste.dayOfTheWeek?.joinToString() +
                    "\nlocationSettings " + teste.locationSettings.toString() +
                    "\nsexSettings " + teste.sexSettings.toString() +
                    "\nfieldOfWorkSettings " + teste.fieldOfWorkSettings.toString()
        )
    }

    fun setViewStandard() {
        var settingsUserView = SettingsManager.getSettings(this)
        var selectedColor: Int
        var textColor: Int

        val period = settingsUserView.period
        if (period != null) {
            if (period.contains(morningCheckBox.text)) {
                morningCheckBox.isChecked = true
            }
            if (period.contains(afternoonCheckBox.text)) {
                afternoonCheckBox.isChecked = true
            }
            if (period.contains(nightCheckBox.text)) {
                nightCheckBox.isChecked = true
            }
        }

        val dayOfTheWeek = settingsUserView.dayOfTheWeek
        if (dayOfTheWeek != null) {
            if (dayOfTheWeek.contains(buttonSunday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonSunday.tag = 1
                buttonSunday.setBackgroundColor(selectedColor)
                buttonSunday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonMonday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonMonday.tag = 1
                buttonMonday.setBackgroundColor(selectedColor)
                buttonMonday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonSaturday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonSaturday.tag = 1
                buttonSaturday.setBackgroundColor(selectedColor)
                buttonSaturday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonWednesday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonWednesday.tag = 1
                buttonWednesday.setBackgroundColor(selectedColor)
                buttonWednesday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonThursday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonThursday.tag = 1
                buttonThursday.setBackgroundColor(selectedColor)
                buttonThursday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonTuesday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonTuesday.tag = 1
                buttonTuesday.setBackgroundColor(selectedColor)
                buttonTuesday.setTextColor(textColor)
            }
            if (dayOfTheWeek.contains(buttonFriday.text)) {
                selectedColor = resources.getColor(R.color.orange)
                textColor = resources.getColor(R.color.white)
                buttonFriday.tag = 1
                buttonFriday.setBackgroundColor(selectedColor)
                buttonFriday.setTextColor(textColor)
            }

        }
        adapterSpinner(
            spinnerlocationRegister,
            settingsUserView.locationSettings.toString(),
            R.array.location
        )
        adapterSpinner(
            settingsSpinnerSexo,
            settingsUserView.sexSettings.toString(),
            R.array.sexSetting
        )
        adapterSpinner(
            settingsSpinnerFieldOfWork,
            settingsUserView.fieldOfWorkSettings.toString(),
            R.array.occupation_area_settings
        )
    }

    fun adapterSpinner(spinner: Spinner, value: String, arrayResId: Int) {
        ArrayAdapter.createFromResource(
            this,
            arrayResId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        selectSpinnerItemByValue(spinner, value)

    }

    private fun selectSpinnerItemByValue(spinner: Spinner, value: String) {
        val adapter = spinner.adapter as ArrayAdapter<CharSequence>
        val position = adapter.getPosition(value)
        if (position >= 0) {
            spinner.setSelection(position)
        }
    }

    fun setSettings() {

        var period = sortDaysOfWeek(period).toTypedArray()
        if(period.isEmpty()){
            period =  arrayOf("Manhã","Tarde","Noite")
        }

        var dayOfTheWeek = sortDaysOfWeek(dayOfTheWeek).toTypedArray()
        if(dayOfTheWeek.isEmpty()){
            dayOfTheWeek =  arrayOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom")
        }

        settingsUser.insertSettings(
            period = period,
            dayOfTheWeek = dayOfTheWeek,
            locationSettings = spinnerlocationRegister.selectedItem.toString(),
            sexSettings = settingsSpinnerSexo.selectedItem.toString(),
            fieldOfWorkSettings = settingsSpinnerFieldOfWork.selectedItem.toString(),
        )
        SettingsManager.setSettings(this, settingsUser)
        var teste = SettingsManager.getSettings(this)
        Log.i(
            "@erika", "period " + teste.period?.joinToString() +
                    "\ndayOfTheWeek  " + teste.dayOfTheWeek?.joinToString() +
                    "\nlocationSettings " + teste.locationSettings.toString() +
                    "\nsexSettings " + teste.sexSettings.toString() +
                    "\nfieldOfWorkSettings " + teste.fieldOfWorkSettings.toString()
        )
    }


}