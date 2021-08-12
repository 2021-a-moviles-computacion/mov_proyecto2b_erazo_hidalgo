package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_home_list_item.view.*

class SearchRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<Home> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return SearchRecyclerAdapter.SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_search_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when (holder) {
            is SearchViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }
    override fun getItemCount(): Int
    {
        return items.size
    }

    fun submitList(homeList: List<Home>)
    {
        items=homeList
    }
    class SearchViewHolder constructor(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)
    {
        val imagenReceta = itemView.img_receta
        val tituloReceta = itemView.et_titulo_receta


        fun bind(home: Home) {
                tituloReceta.setText(home.tituloReceta)
                imagenReceta.setImageResource(home.imagenReceta)
            }
        }
    }