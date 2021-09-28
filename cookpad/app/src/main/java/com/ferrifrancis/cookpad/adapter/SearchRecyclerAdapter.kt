package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import kotlinx.android.synthetic.main.layout_home_list_item.view.*
import kotlinx.android.synthetic.main.layout_search_list_item.view.*

class SearchRecyclerAdapter (val recetaList: ArrayList<RecetaDTO>): RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_list_item,parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val receta: RecetaDTO = this.recetaList[position]
        holder.tituloReceta.setText(receta.tituloReceta.toString())
        holder.imagenReceta.setImageBitmap(receta.imageReceta)
    }

    override fun getItemCount(): Int
    {
        return this.recetaList.size
    }

    class SearchViewHolder constructor(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        //val imagenReceta = itemView.img_receta
        val tituloReceta = itemView.et_titulo_recetabuscar
        val imagenReceta = itemView.img_truco


    }

}