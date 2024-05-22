package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class MatchScreenMentorMoreInfActivity : AppCompatActivity() {
    private lateinit var nameUser: TextView
    private lateinit var oldUser: TextView
    private lateinit var educationalInstitution: TextView
    private lateinit var typeOfTeaching: TextView
    private lateinit var yearOfStart: TextView
    private lateinit var courseStudentMatch: TextView
    private lateinit var duration: TextView
    private lateinit var evaluationNoteView: TextView
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var buttonMatchMentor: ImageButton
    private lateinit var noMatchMentor: ImageButton
    private lateinit var start1: ImageView
    private lateinit var start2: ImageView
    private lateinit var start3: ImageView
    private lateinit var start4: ImageView
    private lateinit var start5: ImageView
    private lateinit var chipGroup: ChipGroup
    private var currentJsonIndex = StaticIndex.currentJsonIndex
    private lateinit var studentList: List<UserData>
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_screen_mentor_more_inf)
        initializeViews()
        repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    studentList = response.body() ?: emptyList()
                    displayUserData(studentList[currentJsonIndex!!])
                } else {
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })

        buttonMatchMentor.setOnClickListener {
            if(macthChenk(studentList[currentJsonIndex!!], user)){
                addMatch()
                val intent = Intent(this@MatchScreenMentorMoreInfActivity, MatchScreenActivity::class.java)
                startActivity(intent)
            }else{
                addPotentialMatch()
            }
            if (currentJsonIndex!! < studentList.size - 1) {
                currentJsonIndex = currentJsonIndex!! + 1
                StaticIndex.currentJsonIndex = currentJsonIndex
            }else{
                clearAndSetCenterText()
            }
            val intent = Intent(this@MatchScreenMentorMoreInfActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
            finish()
        }
        noMatchMentor.setOnClickListener{
            if (currentJsonIndex!! < studentList.size - 1) {
                currentJsonIndex = currentJsonIndex!! + 1
                StaticIndex.currentJsonIndex = currentJsonIndex
            }
            val intent = Intent(this@MatchScreenMentorMoreInfActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
            finish()
        }

        homeButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenMentorMoreInfActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenMentorMoreInfActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener{
            val intent = Intent(this@MatchScreenMentorMoreInfActivity, ChatsActivity::class.java)
            startActivity(intent)
        }

    }
    private fun clearAndSetCenterText() {
        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayout1)
        constraintLayout.removeAllViews()
        val textView = TextView(this).apply {
            id = View.generateViewId()
            text = "Não há mais estudantes para o filtro selecionado"
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

    private fun addMatch(){
        val repository = Repository(this)
        val user = UserInfo.getUserInf(this)
        val  userData = studentList[currentJsonIndex!!]
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
        val  userData = studentList[currentJsonIndex!!]
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
    private fun initializeViews() {
        nameUser = findViewById(R.id.nameUser)
        oldUser = findViewById(R.id.oldUser)
        educationalInstitution = findViewById(R.id.educationalInstitution)
        typeOfTeaching = findViewById(R.id.typeOfTeaching)
        yearOfStart = findViewById(R.id.yearOfStart)
        courseStudentMatch = findViewById(R.id.courseStudentMatch)
        duration = findViewById(R.id.duration)
        chipGroup = findViewById(R.id.chipGroup)

        evaluationNoteView = findViewById(R.id.evaluationNoteView)
        start1 = findViewById(R.id.start1)
        start2 = findViewById(R.id.start2)
        start3 = findViewById(R.id.start3)
        start4 = findViewById(R.id.start4)
        start5 = findViewById(R.id.start5)

        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        buttonMatchMentor = findViewById(R.id.buttonMatchMentor)
        noMatchMentor = findViewById(R.id.noMatchMentor)
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
        oldUser.text = userData.age.toString()
        educationalInstitution.text = userData.educationalInstitution
        typeOfTeaching.text = userData.typeTeaching
        yearOfStart.text = userData.dateStart
        courseStudentMatch.text = userData.course
        duration.text = userData.durationCourse

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