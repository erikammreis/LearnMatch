package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class EditPerfilActivity : AppCompatActivity() {
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var nomeUser: TextView
    private lateinit var cpfUser: TextView
    private lateinit var dtUser: TextView
    private lateinit var sexUser: TextView
    private lateinit var cityPerfil: TextView
    private lateinit var cepUser: TextView
    private lateinit var streetUser: TextView
    private lateinit var ensino: TextView
    private lateinit var typeEsn: TextView
    private lateinit var dtStart: TextView
    private lateinit var durationPerfil: TextView
    private lateinit var course: TextView
    private lateinit var occupationAreaPerfil: TextView
    private lateinit var office: TextView
    private lateinit var experience: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)
        initializeViews()

        homeButtonMenu.setOnClickListener {
            val intent = Intent(this@EditPerfilActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@EditPerfilActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@EditPerfilActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeViews() {
        nomeUser = findViewById(R.id.nomeUser)
        cpfUser = findViewById(R.id.cpfUser)
        dtUser = findViewById(R.id.dtUser)
        sexUser = findViewById(R.id.sexUser)
        cityPerfil = findViewById(R.id.cityPerfil)
        cepUser = findViewById(R.id.cepUser)
        streetUser = findViewById(R.id.streetUser)
        ensino = findViewById(R.id.ensino)
        typeEsn = findViewById(R.id.typeEsn)
        dtStart = findViewById(R.id.dtStart)
        durationPerfil = findViewById(R.id.durationPerfil)
        course = findViewById(R.id.course)
        occupationAreaPerfil = findViewById(R.id.occupationAreaPerfil)
        office = findViewById(R.id.office)
        experience = findViewById(R.id.experience)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
    }

    private fun inicializeView(user: User){
        nomeUser.text = user.name
        cpfUser.text = user.cpf
        dtUser.text = user.dateOfBirth
        sexUser.text = user.sex
        cityPerfil.text = user.city
        cepUser.text = user.cep
        streetUser.text = user.street
        ensino.text = user.educationalInstitution
        typeEsn.text = user.typeTeaching
        dtStart.text = user.dateStart
        durationPerfil.text = user.durationCourse
        course.text = user.course
        occupationAreaPerfil.text =  user.occupationArea
        office.text = user.office
        experience.text = user.experience
        val interest = user.interest
        if (interest != null) {
            addChipGroups(interest)
        } else {
            Log.e("@Erika", "Error ")
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

