package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.R
import kotlinx.coroutines.*


class SplashActivity : AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var intent = Intent(this, LoginActivity::class.java)
        scope.launch {
            delay(1000)
            startActivity(intent)
            finish()
        }
    }
    override fun onPause() {
        scope.cancel()
        super.onPause()

    }
}