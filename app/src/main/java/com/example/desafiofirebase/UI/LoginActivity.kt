package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.desafiofirebase.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvcrateaccount.setOnClickListener {
            var intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            performLogin()
        }


    }
    private fun performLogin(){
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful){
                    return@addOnCompleteListener
                } else {
                    changeActivity()
                }
                Log.d("Main", "Successfully login: ${it.result?.user?.uid}")
            }.addOnFailureListener {
                Log.d("Main", "Falha ao logar: ${it.message}")
            }
    }
    private fun changeActivity(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}