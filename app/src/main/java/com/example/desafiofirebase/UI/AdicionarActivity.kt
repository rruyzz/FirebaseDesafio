package com.example.desafiofirebase.UI


import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R

import com.example.desafiofirebase.databinding.ActivityAdicionarBinding
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso


class AdicionarActivity : AppCompatActivity() {
    private lateinit var bind: ActivityAdicionarBinding

    lateinit var storage: StorageReference
    private var gameinfo: Game = Game()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAdicionarBinding.inflate(layoutInflater)

        bind.btnAddImage.setOnClickListener {
            catchPicture()
        }

        bind.includeCadastroGames.btnSaveGame.setOnClickListener {
            gameinfo.name = bind.includeCadastroGames.edtNameAdd.text.toString()
            gameinfo.data = bind.includeCadastroGames.edtDataAdd.text.toString()
            gameinfo.description = bind.includeCadastroGames.edtDescription.text.toString()
            save()
            finish()

        }
        setContentView(bind.root)
    }

    private fun catchPicture() {
        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Catch Picture"), 1000)
    }


    private fun save() {
        val db = FirebaseFirestore.getInstance().collection("Games")
        val ref = FirebaseDatabase.getInstance().getReference("/games/"  )
        val id = getUniqueId()
        gameinfo.id = id
        ref.child(id!!).setValue(gameinfo)
        db.document(id).set(gameinfo)
    }

    private fun getUniqueId() = FirebaseFirestore.getInstance().collection("key").document().id

    var selectedFotoUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val upFile = storage.putFile(data?.data!!)
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
                    Log.i("URL da Imagem", url)
                    gameinfo.image = url
                    Picasso.get().load(url).into(bind.btnAddImage)
//                    Glide.with(this)
//                        .load(gameinfo.image)
//                        .into(bind.btnAddImage)
                }
            }
        }
    }


}
