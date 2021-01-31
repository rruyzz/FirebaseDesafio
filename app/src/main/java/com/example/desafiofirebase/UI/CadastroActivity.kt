package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiofirebase.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        btnCadastro.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {

        val name = edtNameCadastro.text.toString()
        val email = edtEmailCadastro.text.toString()
        val password = edtPasswordCadastro.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()

        if(password != confirmPassword){
            Toast.makeText(this, "Senhas incorretas", Toast.LENGTH_LONG).show()
            return
        }

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Favor inserir dados corretamente", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                } else {
                    changeActivity()
                }
                Log.d("Main", "Successfully created user uid: ${it.result?.user?.uid}")
            }.addOnFailureListener{
                Log.d("Main", "Falha ao criar usuario: ${it.message}")
            }
    }
    private fun changeActivity(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}