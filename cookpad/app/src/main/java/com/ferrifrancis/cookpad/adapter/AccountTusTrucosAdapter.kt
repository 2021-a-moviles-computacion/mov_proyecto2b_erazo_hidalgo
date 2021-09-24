package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_home_list_item.view.*

class AccountTusTrucosAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<Home> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TusTrucosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_home_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TusTrucosViewHolder ->{
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

    class TusTrucosViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val imagenReceta = itemView.img_vereceta
        val tituloReceta = itemView.et_titulo_receta
        val nombreAutorReceta = itemView.tv_nombre_autor_recete
        val imagenAutorReceta = itemView.img_usuario1

        fun bind(home : Home)
        {
            tituloReceta.setText(home.tituloReceta)
            nombreAutorReceta.setText(home.nombreAutorReceta)
            imagenReceta.setImageResource(home.imagenReceta)
            imagenAutorReceta.setImageResource(home.imagenAutor)
        }
    }
}