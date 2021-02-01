package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.ActivityEditaBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditaActivity : AppCompatActivity() {
    private lateinit var bind: ActivityEditaBinding

    lateinit var storage: StorageReference
    private var gameinfo: Game = Game()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityEditaBinding.inflate(layoutInflater)

        checkInfo()

        bind.includeEditaGames.btnSaveGame.setOnClickListener {
            if (gameinfo != null) {
                save(newGamesInfo(gameinfo))
                finish()
            }else{
                finish()
            }
        }
        bind.btnAddEdtImage.setOnClickListener {
            catchPicture()
        }
        setContentView(bind.root)
    }

    private fun checkInfo(){
        val game = intent.getSerializableExtra("game") as? Game

        if (game != null){
            gameinfo = game
            setInfo(gameinfo)
        }else{
            finish()
        }
    }

    private fun setInfo(gamesInfo: Game){
        bind.includeEditaGames.edtNameAdd.setText(gamesInfo.name)
        bind.includeEditaGames.edtDataAdd.setText(gamesInfo.data)
        bind.includeEditaGames.edtDescription.setText(gamesInfo.description)

        Picasso.get().load(gamesInfo.image).into(bind.btnAddEdtImage)
    }

    private fun save(gamesInfo: Game){
        val db = FirebaseFirestore.getInstance().collection("Games")
        val ref = FirebaseDatabase.getInstance().getReference("/games/"  )
        ref.child(gamesInfo.id).setValue(gameinfo)
        db.document(gamesInfo.id).set(gamesInfo)
    }

    private fun newGamesInfo(game: Game): Game{
        var name = bind.includeEditaGames.edtNameAdd.text.toString()
        var data = bind.includeEditaGames.edtDataAdd.text.toString()
        var description = bind.includeEditaGames.edtDescription.text.toString()
        return Game(name,data, description,game.image, game.id)
    }

    private fun catchPicture() {
        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Catch Imagem"), 1000)
    }

    private fun getUniqueId() = FirebaseFirestore.getInstance().collection("key").document().id

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000) {
            val upFile = storage.putFile(data!!.data!!)
            upFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem carregada", Toast.LENGTH_SHORT).show()
                }
                storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    gameinfo.image = url
                    Picasso.get().load(url).into(bind.btnAddEdtImage)
                }
            }
        }
    }
}