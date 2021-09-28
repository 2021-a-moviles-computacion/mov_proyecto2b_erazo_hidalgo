package com.ferrifrancis.cookpad.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.SearchRecyclerAdapter
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.ferrifrancis.cookpad.dto.TrucoDTO
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    private lateinit var searchAdapter: SearchRecyclerAdapter //es no null,pero se inicializará más luego
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    private lateinit var listaRecetaTemporal: ArrayList<RecetaDTO>
    private lateinit var  db: FirebaseFirestore
    val PATH_IMAGE_RECETA = "Image Receta/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.rv_search)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        this.listaReceta = arrayListOf()
        this.listaRecetaTemporal = arrayListOf()

        this.searchAdapter = SearchRecyclerAdapter(this.listaRecetaTemporal)

        recyclerView.adapter = this.searchAdapter
        eventChangeListener1()
        listenerSearch()

    }

    fun listenerSearch()
    {
        val searchView = requireView().findViewById<SearchView>(R.id.sv_buscar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //cuando el usuario escribe cualquier textp
                listaRecetaTemporal.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    listaReceta.forEach {

                        if(it.tituloReceta != null)
                        {
                            if(it.tituloReceta!!.toLowerCase(Locale.getDefault()).contains(searchText))
                            {
                                listaRecetaTemporal.add(it)
                            }
                        }

                        searchAdapter.notifyDataSetChanged()
                    }

                }else{

                    listaRecetaTemporal.clear()
                    listaRecetaTemporal.addAll(listaReceta)
                    searchAdapter.notifyDataSetChanged()
                }
                return false
            }

        })
    }


    private fun eventChangeListener1() {
        db = FirebaseFirestore.getInstance()
        db.collection("receta")
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

                            val idReceta: String? = doc.document.id
                           // val nombreUsuario: String? = doc.document.get("nombreUsuarioAutor").toString()
                            val tituloReceta: String? = doc.document.get("tituloReceta").toString()
                          //  val uid_usuario: String? = doc.document.get("idUsuario").toString()
                          //  val descripcionTruco: String? = doc.document.get("descripcionTruco").toString()
                            //  val reaccionAplauso: Int? = doc.document.get("reaccionAplauso").toString().toIntOrNull()
                            //  val reaccionCorazon: Int? = doc.document.get("reaccionCorazon").toString().toIntOrNull()



                            val recetaObj = RecetaDTO(tituloReceta  = tituloReceta, uid_receta = idReceta, imageReceta = null )

                            //val receta: RecetaDTO =doc.document.toObject(RecetaDTO::class.java)
                            //receta.imageReceta = receta.uid_receta?.let { cargarImagen(it) }
                            //if(receta.imageReceta == null)
                            //  Log.i("home","receta nulo")
                            //listaReceta.add(recetaObj)
                            cargarImagenYActualizaLista(recetaObj)

                        }
                    }


                }
            })
    }

    fun cargarImagenYActualizaLista(recetaObjeto: RecetaDTO?){

        if(recetaObjeto != null && recetaObjeto.uid_receta != null) {

            val referencia = Firebase.storage
            val nombreImg = referencia.reference.child(PATH_IMAGE_RECETA+recetaObjeto.uid_receta+".jpg")

            nombreImg.getBytes(10024 * 10024)
                .addOnSuccessListener {
                    Log.i("home", "cargar imagen success")
                    val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
                    recetaObjeto.imageReceta = bit
                    this.listaReceta.add(recetaObjeto)
                    searchAdapter.notifyDataSetChanged()
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




}