package com.ferrifrancis.cookpad.fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import com.ferrifrancis.cookpad.adapter.VerRecetaRecyclerAdapter
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.RecursiveAction



class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeRecyclerAdapter
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    private lateinit var  db: FirebaseFirestore
    val PATH_IMAGE = "Image Receta/"





    private lateinit var listaHome: ArrayList<Home>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(itemView, savedInstanceState)


        recyclerView = requireView().findViewById(R.id.rv_home)
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        recyclerView.setHasFixedSize(true)

          this.listaReceta = arrayListOf()
          this.homeAdapter = HomeRecyclerAdapter(this.listaReceta, requireActivity())
          recyclerView.adapter = this.homeAdapter

        eventChangeListener()

    }


    fun cargarImagenYActualizaLista(recetaObjeto: RecetaDTO?){

        if(recetaObjeto != null && recetaObjeto.uid_receta != null) {

            val referencia = Firebase.storage
            val nombreImg = referencia.reference.child(PATH_IMAGE+recetaObjeto.uid_receta+".jpg")

            nombreImg.getBytes(10024 * 10024)
                .addOnSuccessListener {
                    Log.i("home", "cargar imagen success")
                    val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
                    recetaObjeto.imageReceta = bit
                    this.listaReceta.add(recetaObjeto)
                    homeAdapter.notifyDataSetChanged()
                    Log.i("home", "se cargó imagen, se actualizó lista")
                }
                .addOnFailureListener {
                    Log.i("home", "cargar imagen NO success")
                }
        }
        else{
            Log.i("firestorage","no se cargó imagen, no se actualizó lista")
        }

    }


    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("receta")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null)
                    {
                        Log.i("Firestore",error.message.toString())
                    }
                    for( doc: DocumentChange in value?.documentChanges!!)
                    {
                        if(doc.type == DocumentChange.Type.ADDED)
                        {

                            val idReceta: String? = doc.document.id
                            val nombreUsuario: String? = doc.document.get("nombreUsuarioAutor").toString()
                            val nombreReceta: String? = doc.document.get("tituloReceta").toString()
                            val uid_usuario: String? = doc.document.get("uid_usuario").toString()
                            val descripcionReceta: String? = doc.document.get("descripcionReceta").toString()
                            val procedimientoReceta: String? = doc.document.get("procedimientoReceta").toString()
                            val comensales: Int? = doc.document.get("comensales").toString().toIntOrNull()
                            val tiempoElaboracion: String? = doc.document.get("tiempoElaboracion").toString()
                            val paso1: String? = doc.document.get("paso1").toString()
                            val paso2: String? = doc.document.get("paso2").toString()
                            val paso3: String? = doc.document.get("paso3").toString()
                            val paso4: String? = doc.document.get("paso4").toString()
                            val reaccionAplauso: Int? = doc.document.get("reaccionAplauso").toString().toIntOrNull()
                            val reaccionCorazon: Int? = doc.document.get("reaccionCorazon").toString().toIntOrNull()



                            val recetaObj = RecetaDTO(uid_receta = idReceta, tituloReceta = nombreReceta, nombreUsuarioAutor = nombreUsuario,
                                uid_usuario = uid_usuario,descripcionReceta = descripcionReceta, comensales = comensales,paso1=paso1,paso2=paso2,
                                paso3=paso3,paso4=paso4,reaccionAplauso = reaccionAplauso,reaccionCorazon = reaccionCorazon,
                                imageReceta = null,procedimientoReceta = procedimientoReceta, tiempoElaboracion = tiempoElaboracion )

                            //val receta: RecetaDTO =doc.document.toObject(RecetaDTO::class.java)
                            //receta.imageReceta = receta.uid_receta?.let { cargarImagen(it) }
                            //if(receta.imageReceta == null)
                              //  Log.i("home","receta nulo")
                           //listaReceta.add(recetaObj)
                            cargarImagenYActualizaLista(recetaObj)

                        }
                    }
                    //homeAdapter.notifyDataSetChanged()
                    //aqui acabó de cargar los documentos
                    //cargarImagenYActualizaLista()

                }
            })
    }




}