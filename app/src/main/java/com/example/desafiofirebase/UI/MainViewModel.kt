package com.example.desafiofirebase.UI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage

class MainViewModel: ViewModel() {
    val listaGame = MutableLiveData<ArrayList<Game>>()

    var games = mutableListOf<Game>()
    var ref = FirebaseStorage.getInstance()


}