package com.example.desafiofirebase.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.UI.Game
import com.example.desafiofirebase.UI.MainActivity
import com.squareup.picasso.Picasso

class MainAdapter(private val listGame: ArrayList<Game>, val listener: MainActivity) : RecyclerView.Adapter<MainAdapter.GameAdapterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapterViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return GameAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameAdapterViewHolder, position: Int) {
        val currentItem = listGame[position]

        holder.gameTitle.text = currentItem.name
        holder.gameYear.text = currentItem.data


        Picasso.get().load(currentItem.image).into(holder.gameImage)

    }

    override fun getItemCount(): Int {
        return listGame.size
    }

    interface OnGameClick {
        fun onClick(position: Int)
    }

    inner class GameAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var gameImage: ImageView = itemView.findViewById(R.id.ivGame)
        var gameTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var gameYear: TextView = itemView.findViewById(R.id.tvDate)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.onClick(position)
            }
        }
    }

}

