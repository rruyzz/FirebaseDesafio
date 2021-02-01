package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiofirebase.UI.Adapter.MainAdapter
import com.example.desafiofirebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

import androidx.lifecycle.observe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity(), MainAdapter.OnGameClick {
    lateinit var bind: ActivityMainBinding
    lateinit var gamesAdapter: MainAdapter
    lateinit var layoutManager: LinearLayoutManager
    val scope = CoroutineScope(Dispatchers.Main)
    private var listGamesInfo = MutableLiveData<ArrayList<Game>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bind.root)

        layoutManager = GridLayoutManager(this, 2)
        bind.rv.layoutManager = layoutManager
        bind.rv.setHasFixedSize(true)

        listGamesInfo.observe(this, {
            gamesAdapter = MainAdapter(it, this)
            bind.rv.adapter = gamesAdapter
        })

        bind.btnAdd.setOnClickListener {
            startActivity(Intent(this, AdicionarActivity::class.java))
        }

    }

    override fun onClick(position: Int) {
        val intent = Intent(this, GameActivity::class.java)
        val game = listGamesInfo.value?.get(position)

        intent.putExtra("game", game)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getGamesInfo()
    }


    private fun getGamesInfo() {
        val db = Firebase.firestore.collection("Games")
        val listGames = ArrayList<Game>()
        scope.launch {
            val list = db.get().await()
            list.forEach { games ->
                listGames.add(games.toObject())
            }
            listGamesInfo.postValue(listGames)
        }
    }
}