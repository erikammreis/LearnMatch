package br.com.fiap.learnmatch

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MatchScreenStudentActivity : AppCompatActivity() {
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
    private var currentJsonIndex = StaticStudentIndex.currentJsonIndex
    private lateinit var dayWeekLayout: LinearLayout
    private lateinit var dayWeekLayout2: LinearLayout
    private lateinit var studentList: List<UserData>
    private lateinit var repository: Repository
    private lateinit var chipGroup: ChipGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_student)
        initializeViews()

    }
    private fun initializeViews() {
        nameUser = findViewById(R.id.nameUser)
        courseStudentMatch = findViewById(R.id.courseStudentMatch)
        periodAvailable = findViewById(R.id.periodAvailable)
        dayWeekLayout = findViewById(R.id.dayWeekLayout)
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
    }

    private fun displaySettings(userData: UserData, setting: SettingsUser, user: User): Boolean {
        val periodS = setting?.period
        val periodU = userData?.period
        var cont = 0
        Log.i(
            "@erika", userData.name + "\n"
        )

        Log.i(
            "@erika", "periodS " + periodS?.joinToString() +
                    "periodU " + periodU?.joinToString()
        )
        if (periodS != null && periodU != null) {
            if (periodS.any { periodU.contains(it) }) {
                cont = cont + 1
            }
        }
        Log.i(
            "@erika", "setting.sexSettings " + setting.sexSettings +
                    "userData.sex " + userData.sex
        )
        if (setting.sexSettings != null) {
            if ((setting?.sexSettings == userData.sex && user.sex != userData.sexSettings) ||
                setting?.sexSettings.equals("Todos") && (user.sex != userData.sexSettings
                        || userData.sexSettings.equals("Todos"))
            ) {
                cont = cont + 1
                Log.i(
                    "@erika", "setting.sexSettings " + setting.sexSettings +
                            "userData.sex " + userData.sex
                )
            }
        }
        val dayOfTheWeekS = setting?.dayOfTheWeek
        val dayOfTheWeekU = userData?.dayOfTheWeek
        Log.i(
            "@erika", "dayOfTheWeekS " + dayOfTheWeekS?.joinToString() +
                    "dayOfTheWeekU " + dayOfTheWeekU?.joinToString()
        )
        if (dayOfTheWeekS != null && dayOfTheWeekU != null) {
            if (dayOfTheWeekS.any { dayOfTheWeekU.contains(it) }) {
                cont = cont + 1
                Log.i(
                    "@erika", "dayOfTheWeekS " + dayOfTheWeekS.joinToString() +
                            "dayOfTheWeekU " + dayOfTheWeekU.joinToString()
                )
            }
        }

        if (setting.fieldOfWorkSettings != null) {
            if (setting.fieldOfWorkSettings == userData.fieldOfWorkSettings || setting.fieldOfWorkSettings.equals(
                    "Todas"
                )
            ) {
                cont = cont + 1
            }
        }

        if (setting.locationSettings != null) {
            Log.i(
                "@erika", "setting.locationSettings " + setting.locationSettings +
                        "userData.locationSettings " + userData.locationSettings
            )
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
                    Log.i(
                        "@erika", "setting.locationSettings " + setting.locationSettings +
                                "userData.locationSettings " + userData.locationSettings
                    )
                    cont = cont + 1
                }
            }
        }
        if (cont == 3) {
            return true
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