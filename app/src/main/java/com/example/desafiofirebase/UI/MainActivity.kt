package com.example.desafiofirebase.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {
    //    lateinit var adapter : Adapter
    lateinit var layout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd.setOnClickListener {
            var intent = Intent(this, AdicionarActivity::class.java)
            startActivity(intent)
        }
    }
}

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
