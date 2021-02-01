package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.ActivityGameBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGameBinding

    private var gameInfo: Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bind = ActivityGameBinding.inflate(layoutInflater)



        val games = intent.getSerializableExtra("game") as? Game

        if(games != null){
            gameInfo = games
            updateGamesInfo(gameInfo)
        }else{
            Toast.makeText(this, "Não foi possível carregar suas as informações! :(", Toast.LENGTH_SHORT).show()
            finish()
        }
        bind.btnBack.setOnClickListener {
            finish()
        }

        bind.btnEdit.setOnClickListener {
            val intent = Intent(this, EditaActivity::class.java)
            intent.putExtra("game", gameInfo)
            startActivity(intent)
            finish()
        }

        setContentView(bind.root)
    }


    private fun updateGamesInfo(gamesInfo: Game){

        bind.txtName.text = gamesInfo.name
        bind.tvTitle.text = gamesInfo.name
        bind.txtYear.text = gamesInfo.data
        bind.tvDescription.text = gamesInfo.description

//        Picasso.get().load(gamesInfo.image).into(bind.imgGameBack)
        Glide.with(this)
            .load(gamesInfo.image)
            .into(bind.imgGameBack)

    }
}