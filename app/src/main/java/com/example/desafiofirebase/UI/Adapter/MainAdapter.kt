package com.example.desafiofirebase.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofirebase.R
import com.example.desafiofirebase.UI.Game
import com.example.desafiofirebase.UI.MainActivity

class MainAdapter(var listener: OnGameClickListener, var context: MainActivity) :
    RecyclerView.Adapter<MainAdapter.GameAdapterViewHolder>() {
    var listaGame = arrayListOf<Game>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.GameAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return GameAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaGame.size

    fun addGame(lista: ArrayList<Game>) {
        listaGame.addAll(lista)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainAdapter.GameAdapterViewHolder, position: Int) {
        val game = listaGame[position]
        holder.nome.text = game.name.toString()
        holder.date.text = game.data.toString()
        Glide.with(holder.itemView)
            .load(game.image).into(holder.capaGame)

    }


    interface OnGameClickListener {
        fun gameClick(position: Int)
    }

    inner class GameAdapterViewHolder(item: View) : RecyclerView.ViewHolder(item),
        View.OnClickListener {
        val capaGame: ImageView = item.findViewById(R.id.ivGame)
        val nome: TextView = item.findViewById(R.id.tvTitle)
        val date: TextView = item.findViewById(R.id.tvDate)

        init {
            item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.gameClick(position)
            }
        }

    }
}

