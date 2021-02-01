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
//    lateinit var adapter : Adapter
//    lateinit var layout: GridLayoutManager
//    lateinit var itemAdapter: MainAdapter
//    lateinit var gridLayoutManager: GridLayoutManager
//    private var gameList = MutableLiveData<ArrayList<Game>>()
//    val scope = CoroutineScope(Dispatchers.Default)
//    val ref = Firebase.database.reference
//    lateinit var binding: ActivityGameBinding
//
//    lateinit var database: FirebaseDatabase
//    lateinit var reference: DatabaseReference
//    private lateinit var gameAdapter: MainAdapter
//
//    private lateinit var gamesLayoutManager: RecyclerView.LayoutManager
//
//
//    private val gameObj = Firebase.firestore.collection("games")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        catchGames()
//
//        gamesLayoutManager = GridLayoutManager(this, 2)
//        rv.layoutManager = gamesLayoutManager
//        gameAdapter = MainAdapter(this, this)
//        rv.adapter = gameAdapter
//
//        btnAdd.setOnClickListener {
//            var intent = Intent(this, AdicionarActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//    private fun catchGames(){
//        var gamesList = arrayListOf<Game>()
//
//        database = FirebaseDatabase.getInstance()
//        reference = database.getReference("games/")
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataSnapshot.children.forEach {
//                    if (it.key == "games") {
//                        it.children.forEach {
//                            var game = Game(
//                                it.child("id").value.toString(),
//                                it.child("name").value.toString(),
//                                it.child("data").value.toString(),
//                                it.child("description").value.toString(),
//                                it.child("image").value.toString()
//                            )
//                            gamesList.add(game)
//                        }
//                    }
//
//                }
//
//                gameAdapter.addList(gamesList)
//                gamesList = arrayListOf()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_LONG).show()
//            }
//        })
//
//    }
//    fun gameClick(position: Int) {
//
//        var gamesList = gameAdapter.listGame
//        var game = gamesList.get(position)
//
//        var intent = Intent(this, GameActivity::class.java)
//        intent.putExtra("game", game)
//        startActivity(intent)
//    }
//}
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        btnAdd.setOnClickListener {
//            var intent = Intent(this, AdicionarActivity::class.java)
//            startActivity(intent)
//        }
//        binding.rv.adapter = MainAdapter(
//            FirebaseRecyclerOptions.Builder<Game>()
//                .setQuery(
//                    FirebaseDatabase.getInstance()
//                        .reference
//                        .child("games")
//                        .limitToLast(50), Game::class.java)
//                .build()).apply { startListening() }
//
//
//    }
//    }
//
//}


//        gridLayoutManager = GridLayoutManager( this, 2)
//        rv.layoutManager = gridLayoutManager
//        rv.hasFixedSize()
//
//    }
//        fun gameClick(position: Int) {
//            val clickedItem = gameList.value?.get(position)
//            val intent = Intent(this, AdicionarActivity::class.java)
////            intent.putExtra("gameClick", gameClick)
//            startActivity(intent)
//    }
//
//    fun getGames() {
//        val bd = Firebase.firestore.collection("Games")
//        val localGameList = ArrayList<Game>()
//        scope.launch {
//            val remoteGameList = bd.get().await()
//            remoteGameList.forEach { doc ->
//                localGameList.add(doc.toObject())
//            }
//            gameList.postValue(localGameList)
//        }
//    }
//    override fun onResume() {
//        super.onResume()
//        getGames()
//    }
//}

////        val adapter = GroupAdapter<GroupieViewHolder>()
////
////
////        rv.adapter = adapter
//
////        adapter.add(GameItem())
////        adapter.add(GameItem())
////        adapter.add(GameItem())
////        adapter.add(GameItem())
//        catchGames()
//    }
//
////    override fun gameClick(position: Int) {
////        TODO("Not yet implemented")
////    }
////
////    private fun catchGames() {
////        val ref = FirebaseDatabase.getInstance().getReference("/games/")
////        ref.addListenerForSingleValueEvent(object : ValueEventListener {
////
////            override fun onDataChange(snapshot: DataSnapshot) {
////                val adapter = GroupAdapter<GroupieViewHolder>()
////                snapshot.children.forEach {
////                    Log.d("MainActivity", it.toString())
////                    val game = it.getValue(Game::class.java)
////                    if (game != null) {
////                        adapter.add(GameItem(game))
////                    }
////                }
////                adapter.setOnItemClickListener { item, view ->
////                    val intent = Intent(view.context, GameActivity::class.java)
////                    startActivity(intent)
////                }
////                rv.adapter = adapter
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////
////            }
////        })
////    }
////
////}
////
////class GameItem(val game: Game) : Item<GroupieViewHolder>() {
////    override fun getLayout(): Int {
////        return R.layout.item
////    }
////
////    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
////        viewHolder.itemView.tvTitle.text = game.name
////        viewHolder.itemView.tvDate.text = game.data
////        Picasso.get().load(game.image).into(viewHolder.itemView.ivGame)
////
////
////    }
