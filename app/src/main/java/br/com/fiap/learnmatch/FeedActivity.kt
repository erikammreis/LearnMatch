package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {

    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton
    private lateinit var campoFeed: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        initializeViews()
        val user = UserInfo.getUserInf(this)

        var repository = Repository(this)
        repository.getFeed(object : Callback<List<FeedData>> {
            override fun onResponse(
                call: Call<List<FeedData>>,
                response: Response<List<FeedData>>
            ) {
                if (response.isSuccessful) {
                    var feedLists = response.body() ?: emptyList()
                   for (feedList in feedLists){
                       addCustomLayoutsToCampoFeed(feedList )
                   }

                } else {
                    Log.e("@erika" ,"Erro")
                }
            }

            override  fun onFailure(call: Call<List<FeedData>>, t: Throwable) {
                Log.e("@erika" ,"Erro")
            }
        })

        homeButtonMenu.setOnClickListener{
            if(user.type.equals("Mentor")) {
                val intent = Intent(this@FeedActivity, MatchScreenMentorActivity::class.java)
                startActivity(intent)
            }else if(user.type.equals("Student")){
                val intent = Intent(this@FeedActivity, MatchScreenStudentActivity::class.java)
                startActivity(intent)
            }
        }
        PerfilButtonMenu.setOnClickListener{
            val intent = Intent(this@FeedActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener{
            val intent = Intent(this@FeedActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViews() {
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        campoFeed = findViewById(R.id.campoFeed)
    }
    private fun addCustomLayoutsToCampoFeed(feedList: FeedData) {
        // Cria um novo LinearLayout
        val linearLayout2 = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                260.dpToPx(),
                100.dpToPx()
            ).apply {
                bottomMargin = 10.dpToPx() // Define a margem inferior
            }
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.bordeorange2)
            orientation = LinearLayout.VERTICAL
            setPadding(10.dpToPx(), 10.dpToPx(), 10.dpToPx(), 10.dpToPx()) // Define padding

        }

        // Cria um novo TextView para o texto
        val textView = TextView(this).apply {
            text = feedList.text
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK) // Define a cor do texto

        }

        // Adiciona o TextView ao LinearLayout


        // Cria um novo ImageView para a imagem
        val imageView = ImageView(this).apply {
            if (feedList.type == "match") {
                setImageResource(R.drawable.match_icon)
            } else {
                setImageResource(R.drawable.notifimenugreen)
            }
            layoutParams = LinearLayout.LayoutParams(
                40.dpToPx(),
                40.dpToPx()
            )
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        // Adiciona o ImageView ao LinearLayout
        linearLayout2.addView(imageView)
        linearLayout2.addView(textView)
        // Adiciona o LinearLayout ao campoFeed
        campoFeed.addView(linearLayout2)
    }


    // Extensão para converter dp em pixels
    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}
