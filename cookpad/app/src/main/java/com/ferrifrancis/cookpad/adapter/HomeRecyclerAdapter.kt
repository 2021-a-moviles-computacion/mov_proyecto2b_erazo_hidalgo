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


class HomeRecyclerAdapter (
    val recetaList: ArrayList<RecetaDTO>,
    val contexto: Context
    ):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>()
{

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val receta : RecetaDTO = this.recetaList[position]
        holder.imagenReceta.setImageBitmap(receta.imageReceta)
        holder.tituloReceta.setText(receta.tituloReceta)
        holder.nombreAutorReceta.setText(receta.nombreUsuarioAutor)
        holder.chipAplauso.setText(receta.reaccionAplauso.toString())
        holder.chipCorazon.setText(receta.reaccionCorazon.toString())
        holder.imagenUsuario.setImageBitmap(receta.imageUsuario)

        //abrir actividad ver receta
       holder.cardView.setOnClickListener{
            contexto.startActivity(Intent(contexto, VerReceta::class.java).putExtra("receta", receta))
        }

        holder.chipAplauso.setOnCloseIconClickListener{


        holder.chipAplauso.setOnClickListener {
            val db = Firebase.firestore
            //cuantos aplauso tiene una receta
            var contadorAplauso: Int?= 0
            val refCities =
                db.collection("receta")
                    .document(receta.uid_receta!!)
            refCities.get().addOnSuccessListener {
                    document ->
                if (document != null) {

                    contadorAplauso= document.data?.get("reaccionAplauso").toString().toDouble().toInt()

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            //parando todo para sobreescribir en un documento
            db.runTransaction {
                    transaction ->
                val documentoActual = transaction.get(refCities)
                val poblacion = documentoActual.getDouble("reaccionAplauso")
                if(poblacion !=null){
                    val nuevaReaccion = poblacion+1
                    transaction.update(refCities, "reaccionAplauso", nuevaReaccion)
                }
            }
                    //si todo es correcto se suma
                .addOnSuccessListener {
                        holder.chipAplauso.setText(contadorAplauso.toString())
                      //  holder.chipAplauso.setEnabled(false);
                        Log.i("transaccion", "Transaccion completada") }
                .addOnFailureListener{Log.i("transaccion", "Error")}


                    }

        holder.chipCorazon.setOnClickListener{
            val db = Firebase.firestore
            var contadorCorazon: Int?= 0
            val refCities =
                db.collection("receta")
                    .document(receta.uid_receta!!)
            refCities.get().addOnSuccessListener {
                    document ->
                if (document != null) {

                    contadorCorazon= document.data?.get("reaccionCorazon").toString().toDouble().toInt()

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            db.runTransaction {
                    transaction ->
                val documentoActual = transaction.get(refCities)
                val reaccion = documentoActual.getDouble("reaccionCorazon")
                if(reaccion !=null){
                    val nuevaReaccion = reaccion+1
                    transaction.update(refCities, "reaccionCorazon", nuevaReaccion)
                }
            }
                .addOnSuccessListener {
                    holder.chipCorazon.setText(contadorCorazon.toString())
                  //  holder.chipCorazon.setEnabled(false);
                    Log.i("transaccion", "Transaccion completada") }
                .addOnFailureListener{Log.i("transaccion", "Error")}

        }





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
        val imagenReceta = itemView.img_ver_truco
        val tituloReceta = itemView.et_titulo_receta
        val nombreAutorReceta = itemView.tv_nombre_autor_recete
        //val imagenAutorReceta = itemView.img_usuario1
        val stripeMenu = itemView.stripe_menu
        val chipAplauso = itemView.chip_aplauso
        val chipCorazon = itemView.chip_corazon
        val cardView= itemView.card_view_home_receta
        val imagenUsuario = itemView.img_usuario1
        var esFav: Int = 0
        var receta: RecetaDTO?=null
        private lateinit var listaReceta: ArrayList<RecetaDTO>
        var Pos: Int = 0


        init {

            chipCorazon.setOnClickListener {
                aumentarEn1Reaccion(adapterPosition,1)
                chipCorazon.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString())
            }
*/
            stripeMenu.setOnClickListener {
                Log.i("popup-menu","set on click listener popupmenu")
                popupMenu(it)

            }



        fun crearReaccionAplauso(){
            val db= Firebase.firestore

            val nombre_usuario: String? = receta?.nombreUsuarioAutor!!
            val uid_receta: String? = receta?.uid_receta!!
            val nombre_receta: String? = receta?.tituloReceta!!




            Log.i("firebase-firestore","funcoin registrar aplauso coleccion")

            if(nombre_usuario != null && uid_receta != null && nombre_receta != null) {
                Log.i("firebase-firestore","todos los campos no son nulos")

                val nuevoUsuario = hashMapOf<String, Any>(
                    "uid_receta" to uid_receta,
                    "nombre_usuario" to nombre_usuario,
                    "nombre_receta" to nombre_receta,

                    )

                db.collection("FavoritoAplauso")
                    .document(receta?.uid_receta!!)
                    .set(nuevoUsuario)
                    .addOnSuccessListener {
                        Log.i("firebase-firestore","Se registró usuario en la colección")

                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore","No se registró usuario en la colección")
                    }

            }
            else
            {
                Log.i("firebase-login","ERROR")
            }



        }

        fun aumentarEn1Reaccion(indxDato: Int, tipoReaccion: Int) {
            //tipoReaccion 0-> aplauso, 1 -> corazon
            when (tipoReaccion) {
                0 -> {
                    val aplausoNum: Int = Data.listaDatos[indxDato].reaccionAplauso
                    Data.listaDatos[indxDato].reaccionAplauso = aplausoNum + 1

                }
                1 -> {
                    val corazonNum: Int = Data.listaDatos[indxDato].reaccionCorazon
                    Data.listaDatos[indxDato].reaccionCorazon = corazonNum + 1

                }

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