package br.com.fiap.learnmatch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val delayMillis: Long = 4000 // 4 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, MatchScreenStudentActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}
