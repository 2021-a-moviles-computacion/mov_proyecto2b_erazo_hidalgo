package com.ferrifrancis.cookpad.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.dto.RecetaDTO
import kotlinx.android.synthetic.main.layout_home_list_item.view.*


class HomeRecyclerAdapter ( val recetaList: ArrayList<RecetaDTO>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    //private lateinit var  mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
       // mListener = listener
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val receta : RecetaDTO = this.recetaList[position]
        //holder.imagenReceta.setImageResource(receta.)
        holder.tituloReceta.setText(receta.tituloReceta)
        holder.nombreAutorReceta.setText(receta.nombreUsuarioAutor)
        //holder.imagenAutorReceta.setImageResource(receta.)
        //holder.stripeMenu
        holder.chipAplauso.setText(receta.reaccionAplauso.toString())
        holder.chipCorazon.setText(receta.reaccionCorazon.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_list_item,parent, false)
        return HomeViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return this.recetaList.size
    }

    class HomeViewHolder constructor(
        itemView: View/*, listener: onItemClickListener*/
    ) : RecyclerView.ViewHolder(itemView) {
        //val imagenReceta = itemView.img_receta
        val tituloReceta = itemView.et_titulo_receta
        val nombreAutorReceta = itemView.tv_nombre_autor_recete
        //val imagenAutorReceta = itemView.img_usuario1
        val stripeMenu = itemView.stripe_menu
        val chipAplauso = itemView.chip_aplauso
        val chipCorazon = itemView.chip_corazon


        init {
            /*
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }*/
            chipAplauso.setOnClickListener {
                Log.i("home recycler adapter"," posicion item al dar clic en chip ${adapterPosition}")
                Data.aumentarEn1Reaccion(adapterPosition,0)
                chipAplauso.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString());
            }
            chipCorazon.setOnClickListener {
                Data.aumentarEn1Reaccion(adapterPosition,1)
                chipCorazon.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString())
            }

            stripeMenu.setOnClickListener {
                Log.i("popup-menu","set on click listener popupmenu")
                popupMenu(it)

            }
        }


        fun popupMenu(v: View) {
            Log.i("home recycler adapter","popupmenu funcion")
            val popupMenu = PopupMenu(v.getContext(), v)
            popupMenu.inflate(R.menu.menu_home_receta)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.mi_guardar_receta -> {
                        true
                    }
                    R.id.mi_reportar_receta -> {
                        true
                    }
                    else -> true
                }

            }
            popupMenu.show()
            
        }
        /*
        fun anadirLike()
        {
            this.numeroLikes= this.numeroLikes+1
            likesTextView.text= this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
        */
    }



}