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

class MatchScreenStudentActivity : AppCompatActivity() {
    private lateinit var nameUser: TextView
    private lateinit var courseStudentMatch: TextView
    private lateinit var periodAvailable: TextView
    private lateinit var evaluationNoteView: TextView
    private lateinit var moreInformation: ImageButton
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var buttonMatchMentor: ImageButton
    private lateinit var noMatchMentor: ImageButton
    private lateinit var start1: ImageView
    private lateinit var start2: ImageView
    private lateinit var start3: ImageView
    private lateinit var start4: ImageView
    private lateinit var start5: ImageView
    private var currentJsonIndex = StaticIndex.currentJsonIndex
    private lateinit var dayWeekLayout: LinearLayout
    private lateinit var dayWeekLayout2: LinearLayout
    private lateinit var mentorList: List<UserData>
    private lateinit var repository: Repository
    private lateinit var chipGroup: ChipGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_student)
        initializeViews()

        currentJsonIndex = StaticIndex.currentJsonIndex

        val settingUser = SettingsManager.getSettings(this)
        val user = UserInfo.getUserInf(this)

        repository = Repository(this)
        repository.getMentorsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    mentorList = response.body() ?: emptyList()
                    while(currentJsonIndex!! < mentorList.size - 1) {
                        Log.i("@erika" ,"Erro interestEquals teste: " + interestEquals(mentorList[currentJsonIndex!!], user))
                        Log.i("@erika" ,"Erro displaySettings: " + displaySettings(mentorList[currentJsonIndex!!], settingUser, user))
                        if(interestEquals(mentorList[currentJsonIndex!!], user) && displaySettings(mentorList[currentJsonIndex!!], settingUser, user)){
                            displayUserData(mentorList[currentJsonIndex!!])
                            break
                        }else{
                            currentJsonIndex = currentJsonIndex!! + 1
                            StaticIndex.currentJsonIndex = currentJsonIndex
                        }
                    }
                    if(!(currentJsonIndex!! < mentorList.size - 1)){
                        clearAndSetCenterText()
                    }
                } else {
                    Log.e("@erika" ,"Erro")
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })

        buttonMatchMentor.setOnClickListener {
            if(macthChenk(mentorList[currentJsonIndex!!], user)){
                addMatch()
                var addUser = mentorList[currentJsonIndex!!]
                StaticIndex.idUserDatar = addUser.id.toInt()
                val intent = Intent(this@MatchScreenStudentActivity, MatchScreenActivity::class.java)
                startActivity(intent)
                currentJsonIndex = currentJsonIndex!! + 1
                StaticIndex.currentJsonIndex = currentJsonIndex
            }else{
                addPotentialMatch()
            }
            while(currentJsonIndex!! < mentorList.size - 1) {
                if(interestEquals(mentorList[currentJsonIndex!!], user) && displaySettings(mentorList[currentJsonIndex!!], settingUser, user)){
                    displayUserData(mentorList[currentJsonIndex!!])
                    currentJsonIndex = currentJsonIndex!! + 1
                    StaticIndex.currentJsonIndex = currentJsonIndex
                    break
                }else{
                    currentJsonIndex = currentJsonIndex!! + 1
                    StaticIndex.currentJsonIndex = currentJsonIndex
                }
            }
            if(!(currentJsonIndex!! < mentorList.size - 1)){
                clearAndSetCenterText()
            }
        }

        noMatchMentor.setOnClickListener {
            while(!displaySettings(mentorList[currentJsonIndex!!], settingUser, user) && currentJsonIndex!! < mentorList.size - 1) {
                currentJsonIndex = currentJsonIndex!! + 1
                StaticIndex.currentJsonIndex = currentJsonIndex
            }
            if (currentJsonIndex!! < mentorList.size - 1) {
                displayUserData(mentorList[currentJsonIndex!!])
                currentJsonIndex = currentJsonIndex!! + 1
                StaticIndex.currentJsonIndex = currentJsonIndex
            }else{
                clearAndSetCenterText()
            }
        }
        moreInformation.setOnClickListener {
            val intent = Intent(this@MatchScreenStudentActivity, MatchScreenStudentMoreInfActivity::class.java)
            startActivity(intent)
        }

        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenStudentActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@MatchScreenStudentActivity, ChatsActivity::class.java)
            startActivity(intent)
        }

    }
    private fun addMatch(){
        val repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        val  userData = mentorList[currentJsonIndex!!]
        var match = userData.match?.toMutableList()
        var  newpotentialMatch = mutableListOf<Long>()
        if (match != null) {
            newpotentialMatch.addAll(match)
            newpotentialMatch.add(user.id)
        }
        val arrayList: Array<Long> = newpotentialMatch.toTypedArray()
        userData.match = arrayList
        repository.UpdateUser(StaticMethods.getJsonUserDate(this,userData))

        var matchUser = user.match?.toMutableList()
        var  newpotentialMatchUser = mutableListOf<Long>()
        if (matchUser != null) {
            newpotentialMatchUser.addAll(matchUser)
            newpotentialMatchUser.add(user.id)
        }
        val arrayListUser: Array<Long> = newpotentialMatchUser.toTypedArray()
        user.match = arrayListUser
        repository.UpdateUser(StaticMethods.getJsonUser(this,user))


    }

    private fun addPotentialMatch(){
        val repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        val  userData = mentorList[currentJsonIndex!!]
        var potentialMatch = userData.potentialMatch?.toMutableList()
        var  newpotentialMatch = mutableListOf<Long>()
        if (potentialMatch != null) {
            newpotentialMatch.addAll(potentialMatch)
            newpotentialMatch.add(user.id)
        }
        val arrayList: Array<Long> = newpotentialMatch.toTypedArray()
        userData.potentialMatch = arrayList
        repository.UpdateUser(StaticMethods.getJsonUserDate(this,userData))
    }
    private fun macthChenk(userData: UserData, user: User):Boolean {
        val potentialMatch = userData.potentialMatch
        if (potentialMatch != null) {
            if (potentialMatch.contains(user.id)) {
                return true
            }
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
        moreInformation = findViewById(R.id.moreInforMentorMatch)
        buttonMatchMentor = findViewById(R.id.buttonMatchMentor)
        noMatchMentor = findViewById(R.id.noMatchMentor)
    }

    private fun displaySettings(userData: UserData, setting: SettingsUser, user: User): Boolean {
        val periodS = setting?.period?.map { it.toLowerCase() }
        val periodU = userData?.period?.map { it.toLowerCase() }
        var cont = 0


        if (periodS != null && periodU != null) {
            Log.i(
                "@erika", "cont periodS:  " + periodS.joinToString()
            )
            Log.i(
                "@erika", "cont periodU:  " + periodU.joinToString()
            )
            if (periodS.any { it in periodU }) {
                Log.i("@erika", "cont periodS:  " + cont)
                cont += 1
            }
        }
        if (setting.sexSettings != null) {
            if ((setting?.sexSettings == userData.sex && user.sex != userData.sexSettings) ||
                setting?.sexSettings.equals("Todos") && (user.sex == userData.sexSettings
                        || userData.sexSettings.equals("Todos"))
            ) {
                Log.i(
                    "@erika", "cont sexSettings:  " + cont
                )
                cont = cont + 1
            }
        }
        val dayOfTheWeekS = setting?.dayOfTheWeek?.map { it.toLowerCase() }
        val dayOfTheWeekU = userData?.dayOfTheWeek?.map { it.toLowerCase() }
        if (dayOfTheWeekS != null && dayOfTheWeekU != null) {
            Log.i(
                "@erika", "cont dayOfTheWeekS:  " + dayOfTheWeekS.joinToString()
            )
            Log.i(
                "@erika", "cont dayOfTheWeekS:  " + dayOfTheWeekU.joinToString()
            )
            if (dayOfTheWeekS.any { it in dayOfTheWeekU } || dayOfTheWeekU.any { it in dayOfTheWeekS }) {
                Log.i("@erika", "cont dayOfTheWeek:  " + cont)
                cont += 1
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
            if (setting.locationSettings == userData.locationSettings || setting.locationSettings.equals("Todas")) {
                if ((userData.city == user.city && setting.locationSettings == "mesma cidade") ||
                    (userData.state == user.state && setting.locationSettings == "mesmo estado" || (
                            setting.locationSettings.equals("Todas") || userData.locationSettings.equals("Todas")))) {
                    Log.i(
                        "@erika", "cont locationSettings:  " + cont
                    )
                    cont = cont + 1
                }
            }
        }
        Log.i(
            "@erika", "cont:  " + cont
        )
        if (cont == 5) {
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

    private fun clearAndSetCenterText() {
        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayout1)
        constraintLayout.removeAllViews()
        val textView = TextView(this).apply {
            id = View.generateViewId()
            text = "Não há mais mentores para o filtro selecionado"
            setTextColor(resources.getColor(android.R.color.black, theme))
        }
        constraintLayout.addView(textView)
        val constraintSet = ConstraintSet().apply {
            clone(constraintLayout)
            connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            setHorizontalBias(textView.id, 0.5f)
            setVerticalBias(textView.id, 0.5f)
        }
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
        dayWeekLayout.removeAllViews()
        dayWeekLayout2.removeAllViews()

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
                    dayWeekLayout.addView(textView)
                } else {
                    dayWeekLayout2.addView(textView)
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