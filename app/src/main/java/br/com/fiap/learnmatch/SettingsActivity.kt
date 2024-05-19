package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupViews()
        visibilityView()

        buttonApply.setOnClickListener {
            savePeriod()
            Log.i("@erika", "savePeriod" + period.toString())
            setSettings()
             var  teste = SettingsManager.getSettings(this)
            Log.i("@erika", " save: " + teste.period.toString() + " save: "
                    + teste.fieldOfWorkSettings +" save: " + teste.locationSettings +" save: " + teste.sexSettings +" save: " + teste.dayOfTheWeek.toString())
            finish()
            val intent = Intent(this@SettingsActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)

        }
        buttonDefault.setOnClickListener {
            default()
//            val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
//            startActivity(intent)
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
       if(morningCheckBox.isChecked){
           period.add(morningCheckBox.text.toString())
       }
        if(afternoonCheckBox.isChecked){
            period.add(afternoonCheckBox.text.toString())
        }
        if(nightCheckBox.isChecked){
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


    fun default(){
        settingsUser.default()
        SettingsManager.setSettings(this, settingsUser)
        var teste = SettingsManager.getSettings(this)
        Log.i("@erika" , "period " + (teste.period?.joinToString()) +
                "\ndayOfTheWeek  " +teste.dayOfTheWeek?.joinToString()+
                "\nlocationSettings " + teste.locationSettings.toString()+
                "\nsexSettings " + teste.sexSettings.toString() +
                "\nfieldOfWorkSettings " + teste.fieldOfWorkSettings.toString())
    }

    fun setSettings() {
        Log.i("@erika" , "sortDaysOfWeek(period).toTypedArray() " +sortDaysOfWeek(period).toString() +
        "\nsortDaysOfWeek(dayOfTheWeek).toTypedArray()   " +sortDaysOfWeek(dayOfTheWeek).toString()+
        "\nspinnerlocationRegister " + spinnerlocationRegister.selectedItem.toString()+
        "\nsettingsSpinnerFieldOfWork " +settingsSpinnerFieldOfWork.selectedItem.toString())
        settingsUser.insertSettings(

            period = sortDaysOfWeek(period).toTypedArray(),
            dayOfTheWeek = sortDaysOfWeek(dayOfTheWeek).toTypedArray(),
            locationSettings = spinnerlocationRegister.selectedItem.toString(),
            sexSettings = settingsSpinnerSexo.selectedItem.toString(),
            fieldOfWorkSettings = settingsSpinnerFieldOfWork.selectedItem.toString(),
        )
        SettingsManager.setSettings(this, settingsUser)
        var teste = SettingsManager.getSettings(this)
        Log.i("@erika" , "period " +teste.period?.joinToString() +
                "\ndayOfTheWeek  " +teste.dayOfTheWeek?.joinToString()+
                "\nlocationSettings " + teste.locationSettings.toString()+
                "\nsexSettings " + teste.sexSettings.toString() +
                "\nfieldOfWorkSettings " + teste.fieldOfWorkSettings.toString())
    }
    fun isValidete(){

    }


}