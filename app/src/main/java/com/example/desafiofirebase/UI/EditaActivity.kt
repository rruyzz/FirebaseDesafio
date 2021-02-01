package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.ActivityEditaBinding
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
//        setContentView(R.layout.activity_edita_game_cadastro)

        bind = ActivityEditaBinding.inflate(layoutInflater)

        checkInfo()


        bind.includeEditaGames.btnSaveGame.setOnClickListener {
            if (gameinfo != null) {
                saveInfo(newGamesInfo(gameinfo))
                showMsg("Informações atualizadas :)")
                finish()
            }else{
                showMsg("Não foi possivel atualizar :(")
                finish()
            }
        }

        bind.btnAddEdtImage.setOnClickListener {
            getPicture()
        }


        setContentView(bind.root)
    }

    private fun checkInfo(){
        val g = intent.getSerializableExtra("game") as? Game

        if (g != null){
            gameinfo = g

            setInfo(gameinfo)
        }else{
            showMsg("Não deu para carregar suas informações !")
            finish()
        }
    }

    private fun setInfo(gamesInfo: Game){
        bind.includeEditaGames.edtNameAdd.setText(gamesInfo.name)
        bind.includeEditaGames.edtDataAdd.setText(gamesInfo.data)
        bind.includeEditaGames.edtDescription.setText(gamesInfo.description)

        Picasso.get().load(gamesInfo.image).into(bind.btnAddEdtImage)
    }

    private fun saveInfo(gamesInfo: Game){
        val db = FirebaseFirestore.getInstance().collection("Games")
        db.document(gamesInfo.id).set(gamesInfo)
    }

    private fun newGamesInfo(game: Game): Game{
        var name = bind.includeEditaGames.edtNameAdd.text.toString()
        var data = bind.includeEditaGames.edtDataAdd.text.toString()
        var description = bind.includeEditaGames.edtDescription.text.toString()
        return Game(name,data, description,game.image, game.id)
    }

    private fun getPicture() {
        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), 0)
    }

    private fun getUniqueId() = FirebaseFirestore.getInstance().collection("Chave").document().id

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {

            val upFile = storage.putFile(data!!.data!!)
            upFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    showMsg("Imagem Carregada !!!")
                }
                storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("URL da Imagem", url)
                    gameinfo.image = url
                    Picasso.get().load(url).into(bind.btnAddEdtImage)
                }
            }
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}