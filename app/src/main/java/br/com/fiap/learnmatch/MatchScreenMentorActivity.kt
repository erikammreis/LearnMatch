package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchScreenMentorActivity : AppCompatActivity() {
    private lateinit var nameUser: TextView
    private lateinit var courseStudentMatch: TextView
    private lateinit var periodAvailable: TextView
    private lateinit var evaluationNoteView: TextView
    private lateinit var moreIntomation: ImageButton
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var buttonMatchMentor: ImageButton
    private lateinit var noMatchMentor: ImageButton
    private lateinit var start1: ImageView
    private lateinit var start2: ImageView
    private lateinit var start3: ImageView
    private lateinit var start4: ImageView
    private lateinit var start5: ImageView
    private var currentJsonIndex = StaticVal.currentJsonIndex
    private lateinit var dayWeekLayout: LinearLayout
    private lateinit var dayWeekLayout2: LinearLayout
    private lateinit var studentList: List<UserData>
    private lateinit var repository: Repository
    private lateinit var chipGroup: ChipGroup
    private lateinit var constraintLayout1: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_mentor)
        currentJsonIndex = StaticVal.currentJsonIndex
        initializeViews()
        val settingUser = SettingsManager.getSettings(this)
        val user = UserInfo.getUserInf(this)

        repository = Repository(this)
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    studentList = response.body() ?: emptyList()
                    while(!displaySettings(studentList[currentJsonIndex!!], settingUser, user) &&  currentJsonIndex!! < studentList.size - 1) {
                       currentJsonIndex = currentJsonIndex!! + 1
                       StaticVal.currentJsonIndex = currentJsonIndex
                    }
                        displayUserData(studentList[currentJsonIndex!!])
                } else {
                    Log.e("@erika" ,"Erro")
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })
        buttonMatchMentor.setOnClickListener {

//                    if(interestEquals(studentList[currentJsonIndex],user)) {
                while(!displaySettings(studentList[currentJsonIndex!!], settingUser, user) && currentJsonIndex!! < studentList.size - 1) {
                    currentJsonIndex = currentJsonIndex!! + 1
                    StaticVal.currentJsonIndex = currentJsonIndex
                }
            if (currentJsonIndex!! < studentList.size - 1) {
                displayUserData(studentList[currentJsonIndex!!])
                currentJsonIndex = currentJsonIndex!! + 1
                StaticVal.currentJsonIndex = currentJsonIndex


//                    }
            }else{
                clearAndSetCenterText()
            }
        }

        noMatchMentor.setOnClickListener {
            if (currentJsonIndex!! < studentList.size - 1) {
                currentJsonIndex = currentJsonIndex!! + 1
                StaticVal.currentJsonIndex = currentJsonIndex
            }
        }
        moreIntomation.setOnClickListener {
            val intent = Intent(this@MatchScreenMentorActivity, MatchScreenMentorMoreInfActivity::class.java)
            startActivity(intent)
        }

        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenMentorActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenMentorActivity, ChatsActivity::class.java)
            startActivity(intent)
        }

    }


    private fun initializeViews() {
        nameUser = findViewById(R.id.nameUser)
        courseStudentMatch = findViewById(R.id.courseStudentMatch)
        periodAvailable = findViewById(R.id.periodAvailable)
        dayWeekLayout = findViewById(R.id.dayWeekLayout)
        dayWeekLayout2 = findViewById(R.id.dayWeekLayout2)
        chipGroup = findViewById(R.id.chipGroup)

        evaluationNoteView = findViewById(R.id.evaluationNoteView)
        start1 = findViewById(R.id.start1)
        start2 = findViewById(R.id.start2)
        start3 = findViewById(R.id.start3)
        start4 = findViewById(R.id.start4)
        start5 = findViewById(R.id.start5)

        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        moreIntomation = findViewById(R.id.moreIntomation)
        buttonMatchMentor = findViewById(R.id.buttonMatchMentor)
        noMatchMentor = findViewById(R.id.noMatchMentor)
        constraintLayout1 = findViewById(R.id.constraintLayout1)
    }

    private fun displaySettings(userData: UserData, setting: SettingsUser, user: User): Boolean {
        val periodS = setting?.period
        val periodU = userData?.period
        var cont = 0
        Log.i(
            "@erika", userData.name + "\n"
        )
        if (periodS != null && periodU != null) {
            if (periodS.any { periodU.contains(it) }) {
                cont = cont + 1
            }
        }
        if (setting.sexSettings != null) {
            if ((setting?.sexSettings == userData.sex && user.sex != userData.sexSettings) ||
                setting?.sexSettings.equals("Todos") && (user.sex != userData.sexSettings
                        || userData.sexSettings.equals("Todos"))
            ) {
                cont = cont + 1
            }
        }
        val dayOfTheWeekS = setting?.dayOfTheWeek
        val dayOfTheWeekU = userData?.dayOfTheWeek
        if (dayOfTheWeekS != null && dayOfTheWeekU != null) {
            if (dayOfTheWeekS.any { dayOfTheWeekU.contains(it) }) {
                cont = cont + 1
            }
        }

        if (setting.locationSettings != null) {
            if (setting.locationSettings == userData.locationSettings || setting.locationSettings.equals(
                    "Todas"
                )
            ) {
                if ((userData.city == user.city && setting.locationSettings == "mesma cidade") ||
                    (userData.state == user.state && setting.locationSettings == "mesmo estado" || (
                            setting.locationSettings.equals("Todas") || userData.locationSettings.equals(
                                "Todas"
                            )))
                ) {
                    cont = cont + 1
                }
            }
        }
        if (cont == 3) {
            return true
        }

        return false
    }

    private fun interestEquals(userData: UserData, user: User): Boolean {
        val interestS = userData.interest
        val interestU = user.interest
        if (interestS != null && interestU != null) {
            if (interestS.any { interestU.contains(it) }) {
                return true
            }
        }
        return false
    }

    private fun assessment(nota: Int) {
        if (nota == 10) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.star_full_orange)
            start4.setImageResource(R.drawable.star_full_orange)
            start5.setImageResource(R.drawable.star_full_orange)
        } else if (nota == 9) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.star_full_orange)
            start4.setImageResource(R.drawable.star_full_orange)
            start5.setImageResource(R.drawable.baseline_star_half_24_half_orange)
        } else if (nota == 8) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.star_full_orange)
            start4.setImageResource(R.drawable.star_full_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 7) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.star_full_orange)
            start4.setImageResource(R.drawable.baseline_star_half_24_half_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 6) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.star_full_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 5) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.baseline_star_half_24_half_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 4) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.star_full_orange)
            start3.setImageResource(R.drawable.start_empty_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)

        } else if (nota == 3) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.baseline_star_half_24_half_orange)
            start3.setImageResource(R.drawable.start_empty_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 2) {
            start1.setImageResource(R.drawable.star_full_orange)
            start2.setImageResource(R.drawable.start_empty_orange)
            start3.setImageResource(R.drawable.start_empty_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 1) {
            start1.setImageResource(R.drawable.baseline_star_half_24_half_orange)
            start2.setImageResource(R.drawable.start_empty_orange)
            start3.setImageResource(R.drawable.start_empty_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        } else if (nota == 0) {
            start1.setImageResource(R.drawable.start_empty_orange)
            start2.setImageResource(R.drawable.start_empty_orange)
            start3.setImageResource(R.drawable.start_empty_orange)
            start4.setImageResource(R.drawable.start_empty_orange)
            start5.setImageResource(R.drawable.start_empty_orange)
        }
    }

    private fun clearAndSetCenterText() {
        // Obtém a referência ao ConstraintLayout
        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayout1)

        // Remove todas as views filhas
        constraintLayout.removeAllViews()

        // Cria um novo TextView
        val textView = TextView(this).apply {
            id = View.generateViewId()
            text = "Texto Centralizado"
            textSize = 18f // Tamanho do texto
            setTextColor(resources.getColor(android.R.color.black, theme)) // Cor do texto
        }

        // Adiciona o TextView ao ConstraintLayout
        constraintLayout.addView(textView)

        // Configura as restrições para centralizar o TextView
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            setHorizontalBias(textView.id, 0.5f) // Centraliza horizontalmente
            setVerticalBias(textView.id, 0.5f) // Centraliza verticalmente
        }

        // Aplica as restrições
        constraintSet.applyTo(constraintLayout)
    }

    private fun visibilityView(Nota: Int) {
        if (Nota == 0) {
            evaluationNoteView.visibility = View.GONE
            start1.visibility = View.GONE
            start2.visibility = View.GONE
            start3.visibility = View.GONE
            start4.visibility = View.GONE
            start5.visibility = View.GONE
        }
    }

    private fun displayUserData(userData: UserData) {
        nameUser.text = userData.name
        courseStudentMatch.text = userData.course
        if (userData.period.toString() == null || userData.period.toString()
                .isEmpty() || userData.period.toString() == "null"
        ) {
            periodAvailable.text = "Manhã,Tarde,Noite"
        } else {
            periodAvailable.text = userData.period?.joinToString()
        }

        val daysOfWeek = userData.dayOfTheWeek
        if (daysOfWeek != null) {
            daysOfWeek(daysOfWeek)
        } else {
            val daysOfWeek = arrayOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom")
            daysOfWeek(daysOfWeek)
        }

        val interest = userData.interest
        if (interest != null) {
            addChipGroups(interest)
        } else {
            Log.e("@Erika", "Error ")
        }

        val evaluationNote = userData.evaluationNote
        if (evaluationNote != null) {
            if (evaluationNote == 0) {
                visibilityView(0)
            } else {
                assessment(evaluationNote)
            }
        }
    }

    private fun daysOfWeek(daysOfWeek: Array<String>) {
        val linearLayout = findViewById<LinearLayout>(R.id.dayWeekLayout)
        val linearLayout2 = findViewById<LinearLayout>(R.id.dayWeekLayout2)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val layoutParams2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(0, 0, 16, 0)
        layoutParams2.setMargins(0, 0, 16, 0)
        linearLayout.removeAllViews()
        linearLayout2.removeAllViews()

        if (daysOfWeek != null) {
            var cont = 0
            for (day in daysOfWeek) {
                val textView = TextView(this)
                textView.text = day
                textView.layoutParams = layoutParams
                textView.textSize = 16f
                textView.setBackgroundResource(R.drawable.buttonweeksmall)
                textView.setTextColor(Color.parseColor("#FFA500"))
                textView.gravity = Gravity.CENTER
                if (cont > 4) {
                    linearLayout.addView(textView)
                } else {
                    linearLayout2.addView(textView)
                }
                cont = cont + 1

            }
        }
    }

    private fun addChipGroups(items: Array<String>) {
        val linearLayout = findViewById<LinearLayout>(R.id.interestsSkills)
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        linearLayout.removeAllViews()
        chipGroup.removeAllViews()

        for (item in items) {
            val chip = Chip(this)
            chip.text = item
            chip.isChipIconVisible = false
            chip.minHeight = resources.getDimensionPixelSize(R.dimen.chip_min_height)
            chip.minWidth = resources.getDimensionPixelSize(R.dimen.chip_min_width)

            chipGroup.addView(chip)
        }

        // Adiciona o ChipGroup ao LinearLayout após a limpeza
        linearLayout.addView(chipGroup)
    }


}
