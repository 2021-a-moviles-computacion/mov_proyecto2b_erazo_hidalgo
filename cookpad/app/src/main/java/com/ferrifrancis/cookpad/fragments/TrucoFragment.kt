package com.ferrifrancis.cookpad.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import com.ferrifrancis.cookpad.adapter.TrucoRecyclerAdapater
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.ferrifrancis.cookpad.dto.TrucoDTO
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class TrucoFragment: Fragment(){
    private lateinit var homeAdapter: TrucoRecyclerAdapater
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaTruco: ArrayList<TrucoDTO>
    private lateinit var  db: FirebaseFirestore
    val PATH_IMAGE = "Image Truco/"
    val PATH_IMAGE_USUARIO = "image_usuario/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_truco, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(itemView, savedInstanceState)


        recyclerView = requireView().findViewById(R.id.rv_truco)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        this.listaTruco = arrayListOf()
        this.homeAdapter = TrucoRecyclerAdapater(this.listaTruco)
        recyclerView.adapter = this.homeAdapter

        eventChangeListener()

    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("truco")
            .addSnapshotListener(object: EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null)
                    {
                        Log.i("Firestore",error.message.toString())
                    }
                    for( doc: DocumentChange in value?.documentChanges!!)
                    {
                        if(doc.type == DocumentChange.Type.ADDED)
                        {

                            val idTruco: String? = doc.document.id
                            val nombreUsuario: String? = doc.document.get("nombreUsuarioAutor").toString()
                            val nombreTruco: String? = doc.document.get("tituloTruco").toString()
                            val uid_usuario: String? = doc.document.get("idUsuario").toString()
                            val descripcionTruco: String? = doc.document.get("descripcionTruco").toString()
                          //  val reaccionAplauso: Int? = doc.document.get("reaccionAplauso").toString().toIntOrNull()
                          //  val reaccionCorazon: Int? = doc.document.get("reaccionCorazon").toString().toIntOrNull()



                            val trucoObj = TrucoDTO(tituloTruco  = nombreTruco, descripcionTruco = descripcionTruco, idUsuario = uid_usuario,
                                uid_truco = idTruco,nombreUsuarioAutor = nombreUsuario, imageTruco = null )

                            //val receta: RecetaDTO =doc.document.toObject(RecetaDTO::class.java)
                            //receta.imageReceta = receta.uid_receta?.let { cargarImagen(it) }
                            //if(receta.imageReceta == null)
                            //  Log.i("home","receta nulo")
                            //listaReceta.add(recetaObj)
                            cargarImagenYActualizaLista(trucoObj)

                        }
                    }


                }
            })
    }

    fun cargarImagenYActualizaLista(trucoObjeto: TrucoDTO?){

        if(trucoObjeto != null && trucoObjeto.uid_truco != null) {

            val referencia = Firebase.storage
            val nombreImg = referencia.reference.child(PATH_IMAGE+trucoObjeto.uid_truco+".jpg")
            val usuarioImg = referencia.reference.child(PATH_IMAGE_USUARIO+trucoObjeto.idUsuario+".jpg")

            nombreImg.getBytes(10024 * 10024)
                .addOnSuccessListener {
                    Log.i("home", "cargar imagen success")
                    val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
                    trucoObjeto.imageTruco = bit
                    //this.listaTruco.add(trucoObjeto)
                    //homeAdapter.notifyDataSetChanged()
                    //Log.i("home", "se cargó imagen, se actualizó lista")
                    usuarioImg.getBytes(10024 * 10024)
                        .addOnSuccessListener {
                            Log.i("firestorage", "cargar imagen usuario success ${trucoObjeto.idUsuario}")
                            val bit_usuario = BitmapFactory.decodeByteArray(it, 0, it.size)
                            trucoObjeto.imageUsuario = bit_usuario
                            this.listaTruco.add(trucoObjeto)
                            homeAdapter.notifyDataSetChanged()

                        }
                        .addOnFailureListener {
                            Log.i("firestorage", "cargar imagen usuario NO success ${trucoObjeto.idUsuario}")
                        }

                }
                .addOnFailureListener {
                    Log.i("home", "cargar imagen NO success")
                }
        }
        else{
            Log.i("firestorage","no se cargó imagen, no se actualizó lista")
        }

    }




}