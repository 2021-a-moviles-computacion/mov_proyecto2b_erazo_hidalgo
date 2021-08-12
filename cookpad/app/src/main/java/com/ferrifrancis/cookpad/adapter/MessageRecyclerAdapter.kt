package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_message_list_item.view.*

class MessageRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Home> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_message_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MessageViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(homeList: List<Home>)
    {
        items=homeList
    }

    class MessageViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val comentario = itemView.tv_mensaje
        val nombreUsuario = itemView.tv_usuario
        val imagenUsuario = itemView.img_usuario1

        fun bind(home : Home)
        {
            comentario.setText(home.comentario)
            nombreUsuario.setText(home.nombreAutorReceta)
            imagenUsuario.setImageResource(home.imagenAutor)
        }
    }
}