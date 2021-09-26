package com.ferrifrancis.cookpad.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_account_tus_recetas_list_item.view.*
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
        val tituloReceta = itemView.et_titulo_truco1
        val botonImagen: ImageButton = itemView.ib_menu_tusrecetas

        fun bind(home : Home)
        {
            tituloReceta.setText(home.tituloReceta)
            imagenReceta.setImageResource(home.imagenReceta)

            botonImagen.setOnClickListener{
                popupMenu(it)
            }
        }

        fun popupMenu(v: View) {
            Log.i("popup-menu","popupmenu funcion")
            val popupMenu = PopupMenu(v.getContext(), v)
            popupMenu.inflate(R.menu.menu_account_mireceta)
            popupMenu.show()

        }

    }
}