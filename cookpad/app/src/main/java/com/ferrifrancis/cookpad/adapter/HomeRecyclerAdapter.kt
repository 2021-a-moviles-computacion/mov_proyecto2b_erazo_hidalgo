package com.ferrifrancis.cookpad.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.layout_home_list_item.view.*


class HomeRecyclerAdapter (
    val recetaList: ArrayList<RecetaDTO>,
    val contexto: Context



    ):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>()
{

    //private lateinit var  mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
       // mListener = listener
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        //val receta1= recetaList[position]
        val receta : RecetaDTO = this.recetaList[position]
        holder.imagenReceta.setImageBitmap(receta.imageReceta)
        holder.tituloReceta.setText(receta.tituloReceta)
        holder.nombreAutorReceta.setText(receta.nombreUsuarioAutor)
        holder.chipAplauso.setText(receta.reaccionAplauso.toString())
        holder.chipCorazon.setText(receta.reaccionCorazon.toString())
        //abrir actividad ver receta
       holder.cardView.setOnClickListener{
            contexto.startActivity(Intent(contexto, VerRecetaRecyclerAdapter::class.java).putExtra("receta", receta))
        }
        holder.chipAplauso.setOnCloseIconClickListener{

                val db = Firebase.firestore
                val refCities =
                    db.collection("receta")
                        .document(receta.uid_receta!!)
                db.runTransaction {
                        transaction ->
                    val documentoActual = transaction.get(refCities)
                    //OBTENIENDO EL VALOR ACTUAL DEL DOCUMENTO
                    val aplauso = documentoActual.get("reacionAplauso").toString().toInt()

                    if(aplauso !=null){
                        val nuevaReaccionAplauso = aplauso+1
                        transaction.update(refCities, "reaccionAplauso", nuevaReaccionAplauso)
                    }
                }
                    .addOnSuccessListener { Log.i("transaccion", "Transaccion completada") }
                    .addOnFailureListener{ Log.i("transaccion", "Error")}



        }





    }

    fun transaccion(){
        val db = Firebase.firestore
        val refCities =
            db.collection("receta")
                .document("BJ")
        db.runTransaction {
                transaction ->
            val documentoActual = transaction.get(refCities)
            val aplauso = documentoActual.getDouble("aplauso")
            val corazon = documentoActual.getDouble("corazon")
            if(aplauso !=null && corazon !=null){
                val nuevaReaccionAplauso = aplauso+1
                val nuevaReaccionCorazon = corazon+1
                transaction.update(refCities, "reaccionAplauso", nuevaReaccionAplauso)
                transaction.update(refCities, "reaccionCorazon", nuevaReaccionCorazon)

            }
        }
            .addOnSuccessListener { Log.i("transaccion", "Transaccion completada") }
            .addOnFailureListener{ Log.i("transaccion", "Error")}
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
        val imagenReceta = itemView.img_vereceta
        val tituloReceta = itemView.et_titulo_receta
        val nombreAutorReceta = itemView.tv_nombre_autor_recete
        //val imagenAutorReceta = itemView.img_usuario1
        val stripeMenu = itemView.stripe_menu
        val chipAplauso = itemView.chip_aplauso
        val chipCorazon = itemView.chip_corazon
        val cardView= itemView.card_view_home_receta



        init {
            /*
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }*/
      /*      chipAplauso.setOnClickListener {

                Log.i("home recycler adapter"," posicion item al dar clic en chip ${adapterPosition}")
                aumentarEn1Reaccion(adapterPosition,0)
                chipAplauso.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString());
            }*/
            chipCorazon.setOnClickListener {
                aumentarEn1Reaccion(adapterPosition,1)
                chipCorazon.setText(Data.listaDatos[adapterPosition].reaccionAplauso.toString())
            }

            stripeMenu.setOnClickListener {
                Log.i("popup-menu","set on click listener popupmenu")
                popupMenu(it)

            }
      /*      val cardView= itemView.card_view_home_receta
            cardView.setOnClickListener {
                contexto.startActivity(Intent(contexto, VerRecetaRecyclerAdapter::class.java).putExtra("receta", this.recetaList))
            }*/


        }
        private fun verReceta(position: Int){

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