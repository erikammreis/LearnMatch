package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsActivity : AppCompatActivity() {
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var linearLayoutmacth: LinearLayout
    private lateinit var LinearLayoutchats: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        initializeViews()
        val user = UserInfo.getUserInf(this)
        Log.i("@erika","ChatsActivity" + user.match)
        Log.i("@erika","ChatsActivity" + user.chats)
        addImageButtonsToLayout(user.match!!)
        var repository = Repository(this)
        repository.getStudentsFromApi(object : Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    var studentLists = response.body() ?: emptyList()
                    for (studentList in studentLists) {
                        if(user.chats!!.contains(studentList.id)){
                            addLayoutsToParent(user.chats!!,studentList)
                        }
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
            }
        })
        homeButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatsActivity, MatchScreenMentorActivity::class.java)
            startActivity(intent)
        }
        PerfilButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatsActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener {
            val intent = Intent(this@ChatsActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initializeViews() {
        linearLayoutmacth = findViewById(R.id.linearLayoutmacth)
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        LinearLayoutchats = findViewById(R.id.LinearLayoutchats)
    }
    private fun addImageButtonsToLayout(items:  Array<Long>) {
        linearLayoutmacth.removeAllViews()

        for (item in items) {
            val imageButton = ImageButton(this)

            imageButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            imageButton.setImageResource(R.drawable.perfilphot)
            imageButton.setBackgroundColor(Color.parseColor("#00FFFFFF"))

            imageButton.setOnClickListener {
               StaticIndex.idUserDatar = item.toInt()
                val intent = Intent(this@ChatsActivity, ChatActivity::class.java)
                startActivity(intent)
            }


            linearLayoutmacth.addView(imageButton)
        }
    }

    private fun addLayoutsToParent(ids: Array<Long>,userData: UserData) {
        LinearLayoutchats.removeAllViews()

        for (id in ids) {
            // Cria um novo LinearLayout
            val linearLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(Color.parseColor("#00FFFFFF"))
            }

            // Cria um novo Button
            val button = Button(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    60.dpToPx()
                ).apply {
                    bottomMargin = 10.dpToPx() // Define a margem inferior
                }
                setBackgroundColor(Color.parseColor("#4FFD9E00"))
                gravity = Gravity.CENTER_VERTICAL
                text = userData.name
                setTextColor(Color.parseColor("#272727"))
            }

            button.setOnClickListener{
                StaticIndex.idUserDatar = id.toInt()
                val intent = Intent(this@ChatsActivity, ChatActivity::class.java)
                startActivity(intent)
            }
            linearLayout.addView(button)

            LinearLayoutchats.addView(linearLayout)
        }
    }

    fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }


}