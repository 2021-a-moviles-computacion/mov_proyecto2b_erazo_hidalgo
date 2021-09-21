package com.ferrifrancis.cookpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.ClasesParcelables.BIngrediente
import com.ferrifrancis.cookpad.R

class IngredientesRecyclerAdapter(
    private val contexto: VerRecetaRecyclerAdapter,
    private val ListaIngredientes: List<BIngrediente>,
    private val recyclerView: RecyclerView

):RecyclerView.Adapter<IngredientesRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cantidadIngrediente: TextView
        val nombreIngrediente: TextView
        val imgOpciones: ImageView

        init{
            cantidadIngrediente= view.findViewById(R.id.et_cantidad_ingrediente)
            nombreIngrediente= view.findViewById(R.id.et_nombre_ingrediente)
            imgOpciones= view.findViewById(R.id.img_opciones)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.layout_ingredientes_verreceta,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredientes = ListaIngredientes[position]
        holder.cantidadIngrediente.text = ingredientes.cantidadIngrediente
        holder.nombreIngrediente.text = ingredientes.nombreIngrediente
        holder.imgOpciones.setImageResource(ingredientes.imgOpciones)


    }

    override fun getItemCount(): Int {
        return ListaIngredientes.size
    }


}