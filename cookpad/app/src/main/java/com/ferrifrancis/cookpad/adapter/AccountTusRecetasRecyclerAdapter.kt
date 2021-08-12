package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_account_tus_recetas_list_item.view.*
import kotlinx.android.synthetic.main.layout_home_list_item.view.*
import kotlinx.android.synthetic.main.layout_home_list_item.view.et_titulo_receta

class AccountTusRecetasRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Home> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TusRecetasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_account_tus_recetas_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TusRecetasViewHolder ->{
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


    class TusRecetasViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val imagenReceta = itemView.imagen_tus_recetas
        val tituloReceta = itemView.et_titulo_receta


        fun bind(home : Home)
        {
            tituloReceta.setText(home.tituloReceta)
            imagenReceta.setImageResource(home.imagenReceta)
        }
    }
}