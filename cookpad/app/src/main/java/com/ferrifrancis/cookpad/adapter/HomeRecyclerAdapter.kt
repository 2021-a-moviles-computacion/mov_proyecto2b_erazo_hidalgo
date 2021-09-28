package com.ferrifrancis.cookpad.adapter


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.activities.VerReceta
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.layout_home_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class HomeRecyclerAdapter (
    val recetaList: ArrayList<RecetaDTO>,
    val contexto: Context,

    ):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

         val receta: RecetaDTO = this.recetaList[position]
        //aunque parezca q es nulo no es nulo

        holder.imagenReceta.setImageBitmap(receta!!.imageReceta)
        holder.tituloReceta.setText(receta!!.tituloReceta)
        holder.nombreAutorReceta.setText(receta!!.nombreUsuarioAutor)
        holder.chipAplauso.setText(receta!!.reaccionAplauso.toString())
        holder.chipCorazon.setText(receta!!.reaccionCorazon.toString())
        holder.imagenUsuario.setImageBitmap(receta!!.imageUsuario)

        //abrir actividad ver receta
        holder.cardView.setOnClickListener {
            contexto.startActivity(
                Intent(contexto, VerReceta::class.java).putExtra(
                    "receta",
                    receta
                )
            )
        }


            holder.chipAplauso.setOnClickListener {
                val db = Firebase.firestore
                //cuantos aplauso tiene una receta
                var contadorAplauso: Int? = 0
                val refCities =
                    db.collection("receta")
                        .document(receta.uid_receta!!)
                refCities.get().addOnSuccessListener { document ->
                    if (document != null) {

                        contadorAplauso =
                            document.data?.get("reaccionAplauso").toString().toDouble().toInt()

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                //parando todo para sobreescribir en un documento
                db.runTransaction { transaction ->
                    val documentoActual = transaction.get(refCities)
                    val poblacion = documentoActual.getDouble("reaccionAplauso")
                    if (poblacion != null) {
                        val nuevaReaccion = poblacion + 1
                        transaction.update(refCities, "reaccionAplauso", nuevaReaccion)
                    }
                }
                    //si todo es correcto se suma
                    .addOnSuccessListener {
                        contadorAplauso= contadorAplauso?.plus(1)
                        holder.chipAplauso.setText(contadorAplauso.toString())
                        //  holder.chipAplauso.setEnabled(false);
                        Log.i("transaccion", "Transaccion completada")
                    }
                    .addOnFailureListener { Log.i("transaccion", "Error") }


            }

            holder.chipCorazon.setOnClickListener {
                val db = Firebase.firestore
                var contadorCorazon: Int? = 0
                val refCities =
                    db.collection("receta")
                        .document(receta?.uid_receta!!)
                refCities.get().addOnSuccessListener { document ->
                    if (document != null) {

                        contadorCorazon =
                            document.data?.get("reaccionCorazon").toString().toDouble().toInt()

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                db.runTransaction { transaction ->
                    val documentoActual = transaction.get(refCities)
                    val reaccion = documentoActual.getDouble("reaccionCorazon")
                    if (reaccion != null) {
                        val nuevaReaccion = reaccion + 1
                        transaction.update(refCities, "reaccionCorazon", nuevaReaccion)
                    }
                }
                    .addOnSuccessListener {
                        contadorCorazon= contadorCorazon?.plus(1)
                        holder.chipCorazon.setText(contadorCorazon.toString())
                        //  holder.chipCorazon.setEnabled(false);
                        Log.i("transaccion", "Transaccion completada")
                    }
                    .addOnFailureListener { Log.i("transaccion", "Error") }

            }


        }


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_list_item, parent, false)
                return HomeViewHolder(itemView)
            }


            override fun getItemCount(): Int {
                return this.recetaList.size
            }

            class HomeViewHolder constructor(
                itemView: View, /*, listener: onItemClickListener*/

            ) : RecyclerView.ViewHolder(itemView) {
                val imagenReceta = itemView.img_ver_truco
                val tituloReceta = itemView.et_titulo_receta
                val nombreAutorReceta = itemView.tv_nombre_autor_recete

                val stripeMenu = itemView.stripe_menu
                val chipAplauso = itemView.chip_aplauso
                val chipCorazon = itemView.chip_corazon
                val cardView = itemView.card_view_home_receta
                val imagenUsuario = itemView.img_usuario1



                init {

                 /*   chipCorazon.setOnClickListener {
                        aumentarEn1Reaccion(adapterPosition, 1)
                        chipCorazon.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString())
                    }*/




                    stripeMenu.setOnClickListener {
                        Log.i("popup-menu", "set on click listener popupmenu")
                        popupMenu(it)

                    }
                }




                    fun popupMenu(v: View) {
                        Log.i("home recycler adapter", "popupmenu funcion")
                        val popupMenu = PopupMenu(v.getContext(), v)
                        popupMenu.inflate(R.menu.menu_home_receta)
                        popupMenu.setOnMenuItemClickListener {
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

                // crear una coleccion que tenga las reaccionesAplauso de una receta
                //dentro de un documento vamos a guardar un usuario, el que ha dado like

                



                }


            }

