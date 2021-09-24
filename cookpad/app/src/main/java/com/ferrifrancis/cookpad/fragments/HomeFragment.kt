package com.ferrifrancis.cookpad.fragments

<<<<<<< HEAD
import android.graphics.Bitmap
import android.graphics.BitmapFactory
=======
import android.content.Context
import android.content.Intent
>>>>>>> main
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
<<<<<<< HEAD
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
=======
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.RecursiveAction
>>>>>>> main


class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeRecyclerAdapter
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    private lateinit var  db: FirebaseFirestore
    val PATH_IMAGE = "Image Receta/"





    private lateinit var listaHome: ArrayList<Home>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

<<<<<<< HEAD
=======




>>>>>>> main
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
          this.homeAdapter = HomeRecyclerAdapter(this.listaReceta,  )
          recyclerView.adapter = this.homeAdapter

        eventChangeListener()

    }

<<<<<<< HEAD
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
=======


>>>>>>> main

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

                            val recetaObj = RecetaDTO(uid_receta = idReceta, tituloReceta = nombreReceta, nombreUsuarioAutor = nombreUsuario,
                                uid_usuario = null,descripcionReceta = null, comensales = null,paso1=null,paso2=null,
                                paso3=null,paso4=null,reaccionAplauso = null,reaccionCorazon = null,
                                imageReceta = null,procedimientoReceta = null, tiempoElaboracion = null )

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