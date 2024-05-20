package br.com.fiap.learnmatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notificationView = findViewById(R.id.notificationView)

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


    }
}