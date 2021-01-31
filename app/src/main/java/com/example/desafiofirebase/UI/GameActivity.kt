package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.desafiofirebase.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val title = intent.getStringExtra("Title")
        val descritpion = intent.getStringExtra("Description")
        val date = intent.getStringExtra("Year")


        tvTitle.text = title
        tvDescription.text = descritpion
        txtName.text = title
        txtYear.text = date

        btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }
}