package com.example.desafiofirebase.UI

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.desafiofirebase.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_adicionar.*
import java.util.*

class AdicionarActivity : AppCompatActivity() {

    val filename = UUID.randomUUID().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        btnAddImage.setOnClickListener {
            Log.d("AdicionarActivity", "Try to show image")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0 )
        }
        btnSaveGame.setOnClickListener{
            upDateImageFireBase()
            saveGameToFireBaseDataBase(filename)
        }
    }

    var selectedFotoUri : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("AdicionarActivity", "Foto selecionada")

            selectedFotoUri = data.data
            //val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedFotoUri)
            //val bitmapDrawable = BitmapDrawable(bitmap).
        }
    }
    private fun upDateImageFireBase(){
        if(selectedFotoUri == null) return
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        ref.putFile(selectedFotoUri!!)
            .addOnSuccessListener {
                Log.d("AdicionarActivity", "Image uploaded: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("AdicionarActivity", "File location: $it")
                    //saveUserToFireBaseDataBase(it.toString())
                }
            }
    }
    private fun saveGameToFireBaseDataBase(Image: String){

        val ref = FirebaseDatabase.getInstance().getReference("/games/"  )
        val name = edtNameAdd.text.toString()
        val descricao = edtDescription.text.toString()
        val data = edtDataAdd.text.toString()
        val gid =  ref.push().key
        val id =  (1000..20000).random()
        val game = Game(id, name, data, descricao, Image)

        ref.child(gid!!).setValue(game)

    }
}