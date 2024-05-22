package br.com.fiap.learnmatch

import UserInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var PerfilButtonMenu: ImageButton
    private lateinit var chatsButtonMenu: ImageButton
    private lateinit var homeButtonMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        initializeViews()
        val user = UserInfo.getUserInf(this)
        var repository = Repository(this)
        repository.getNotificationsFromApi(object : Callback<List<NotificationData>> {
            override fun onResponse(
                call: Call<List<NotificationData>>,
                response: Response<List<NotificationData>>
            ) {
                if (response.isSuccessful) {
                    var notificationList = response.body() ?: emptyList()
                    notificationAdapter = NotificationAdapter(notificationList)
                    notificationView.adapter = notificationAdapter

                } else {
                    Log.e("@erika" ,"Erro")
                }
            }

            override  fun onFailure(call: Call<List<NotificationData>>, t: Throwable) {
                Log.e("@erika" ,"Erro")
            }
        })

        homeButtonMenu.setOnClickListener{
            if(user.type.equals("Mentor")) {
                val intent = Intent(this@NotificationActivity, MatchScreenMentorActivity::class.java)
                startActivity(intent)
            }else if(user.type.equals("Student")){
                val intent = Intent(this@NotificationActivity, MatchScreenStudentActivity::class.java)
                startActivity(intent)
            }
        }
        PerfilButtonMenu.setOnClickListener{
            val intent = Intent(this@NotificationActivity, PerfilActivity::class.java)
            startActivity(intent)
        }
        chatsButtonMenu.setOnClickListener{
            val intent = Intent(this@NotificationActivity, ChatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViews() {
        homeButtonMenu = findViewById(R.id.homeButtonMenu)
        PerfilButtonMenu = findViewById(R.id.PerfilButtonMenu)
        chatsButtonMenu = findViewById(R.id.chatsButtonMenu)
        notificationView = findViewById(R.id.notificationView)
    }
}