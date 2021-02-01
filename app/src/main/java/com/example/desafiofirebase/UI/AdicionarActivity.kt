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
    ////    private fun upDateImageFireBase(){
////        if(selectedFotoUri == null) return
////        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
////
////        ref.putFile(selectedFotoUri!!)
////            .addOnSuccessListener {
////                Log.d("AdicionarActivity", "Image uploaded: ${it.metadata?.path}")
////
////                ref.downloadUrl.addOnSuccessListener {
////                    Log.d("AdicionarActivity", "File location: $it")
////                    //saveUserToFireBaseDataBase(it.toString())
////                }
////            }
////    }

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
//
////    private lateinit var bind: ActivityGameBinding
////
////    lateinit var storage: StorageReference
////    private val CODE_IMG = 100
////    private var gameinfo: Game = Game()
////
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        bind = ActivityGameBinding.inflate(layoutInflater)
////
/////*        setContentView(R.layout.activity_game_cadastro)*/
////
////
////        bind.imgGameBack.setOnClickListener {
////            getPicture()
////        }
////
////        bind.includeCadastroGames.btnSaveGame.setOnClickListener {
////            gameinfo.titulo = bind.includeCadastroGames.nameGame.text.toString()
////            gameinfo.data_lancamento = bind.includeCadastroGames.edDate.text.toString()
////            gameinfo.descricao = bind.includeCadastroGames.edDescription.text.toString()
////
////            if (gameinfo.imgURL == "") {
////                showMsg("Adicione imagem")
////            } else {
////
////                saveInfo()
////                finish()
////            }
////        }
////
////        setContentView(bind.root)
////
////
////    }
////
////    private fun getPicture() {
////        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
////        val intent = Intent()
////        intent.type = "image/"
////        intent.action = Intent.ACTION_GET_CONTENT
////        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), CODE_IMG)
////    }
////
////    private fun saveInfo() {
////        val db = FirebaseFirestore.getInstance().collection("Games")
////        val id = getUniqueId()
////        gameinfo.id = id
////        db.document(id).set(gameinfo)
////    }
////
////    private fun getUniqueId() = FirebaseFirestore.getInstance().collection("Chave").document().id
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////
////        if (requestCode == CODE_IMG) {
////
////            val upFile = storage.putFile(data!!.data!!)
////            upFile.continueWithTask { task ->
////                if (task.isSuccessful) {
////                    showMsg("Imagem Carregada !!!")
////                }
////                storage!!.downloadUrl
////            }.addOnCompleteListener { task ->
////                if (task.isSuccessful) {
////                    val downloadUri = task.result
////                    val url = downloadUri!!.toString()
////                        .substring(0, downloadUri.toString().indexOf("&token"))
////                    Log.i("URL da Imagem", url)
////                    gameinfo.imgURL = url
////                    Picasso.get().load(url).into(bind.imgGame)
////                }
////            }
////        }
////    }
////
////
////    private fun showMsg(msg: String) {
////        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
////    }
//
//    //Funções complementares
//
////    fun dataVerification(): Boolean {
////        var name = tv_registerGameName.text.toString()
////        var yearCreation = tv_registerGameCreated.text.toString()
////
////        var nameVerification = name.isNotEmpty()
////        var yearCreationVerification = true
////
////        if (yearCreation.length != 0)
////            yearCreationVerification = (yearCreation.length == 4)
////
////        //Informa ao usuário o que está inválido
////        if (nameVerification == false)
////            Toast.makeText(this, "Game name is required", Toast.LENGTH_LONG).show()
////
////        if (yearCreationVerification == false)
////            Toast.makeText(this, "Year of Creation invalid", Toast.LENGTH_LONG).show()
////
////        return nameVerification && yearCreationVerification
////    }
//
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        //Como a metodologia adotada é: Criar uma "reserva" para o novo game no Firebase Realtime
//        //antes do usuário preencher os campos, caso ele não salve o novo game, é preciso excluir
//        //a "reserva" feita.
//        if (saveGame == false && isNewGame == true) {
//            deleteGame()
//        }
//    }
//
//    fun callHome() {
//        var intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("userId", userId)
//        startActivity(intent)
//    }
//
//    fun callGameDetailsPage(){
//        var intent = Intent(this, GameActivity::class.java)
//        intent.putExtra("userId", userId)
//        intent.putExtra("game", updatedGame)
//
//        startActivity(intent)
//    }
//}
//
//
////
////        btnAddImage.setOnClickListener {
////            Log.d("AdicionarActivity", "Try to show image")
////
////            val intent = Intent(Intent.ACTION_PICK)
////            intent.type="image/*"
////            startActivityForResult(intent, 0 )
////        }
////        btnSaveGame.setOnClickListener{
////            upDateImageFireBase()
////            saveGameToFireBaseDataBase(filename)
////        }
////    }
////
////    var selectedFotoUri : Uri? = null
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
////            Log.d("AdicionarActivity", "Foto selecionada")
////
////            selectedFotoUri = data.data
////            //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedFotoUri)
////            //val bitmapDrawable = BitmapDrawable(bitmap).
////        }
////    }
////    private fun upDateImageFireBase(){
////        if(selectedFotoUri == null) return
////        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
////
////        ref.putFile(selectedFotoUri!!)
////            .addOnSuccessListener {
////                Log.d("AdicionarActivity", "Image uploaded: ${it.metadata?.path}")
////
////                ref.downloadUrl.addOnSuccessListener {
////                    Log.d("AdicionarActivity", "File location: $it")
////                    //saveUserToFireBaseDataBase(it.toString())
////                }
////            }
////    }
////    private fun saveGameToFireBaseDataBase(Image: String){
////
////        val ref = FirebaseDatabase.getInstance().getReference("/games/"  )
////        val name = edtNameAdd.text.toString()
////        val descricao = edtDescription.text.toString()
////        val data = edtDataAdd.text.toString()
////        val gid =  ref.push().key
////        val id =  (1000..20000).random().toString()
////        val game = Game(id, name, data, descricao, Image)
////
////        ref.child(gid!!).setValue(game)
////
////    }
////}