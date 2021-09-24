package com.ferrifrancis.cookpad.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.VerRecetaRecyclerAdapter
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IngredientsFragment (
    private val contexto: VerRecetaRecyclerAdapter,
    private val ListaReceta: List<RecetaDTO>,
    private val recyclerView: RecyclerView



///hola
): RecyclerView.Adapter<IngredientsFragment.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombreUsuario: TextView
        val tituloReceta: TextView
        val imagenReceta: ImageView



        init {

            nombreUsuario= view.findViewById(R.id.nombreUsuarioVerReceta)
            tituloReceta=view.findViewById(R.id.tituloVerReceta)
            imagenReceta=view.findViewById(R.id.imageVerReceta)


        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.activity_ver_receta_recycler_adapter, //definir vista del recycler view
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val receta = ListaReceta[position]
        holder.tituloReceta.text= receta.tituloReceta
        holder.nombreUsuario.text=receta.nombreUsuarioAutor

    }




    override fun getItemCount(): Int {
        return  ListaReceta.size

    }




}


