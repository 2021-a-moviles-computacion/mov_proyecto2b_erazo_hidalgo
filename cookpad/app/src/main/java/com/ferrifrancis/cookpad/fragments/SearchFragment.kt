package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.adapter.SearchRecyclerAdapter
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    private lateinit var searchAdapter: SearchRecyclerAdapter //es no null,pero se inicializará más luego
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    private lateinit var listaRecetaTemporal: ArrayList<RecetaDTO>
    private lateinit var  db: FirebaseFirestore


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
        eventChangeListener()
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

    private fun eventChangeListener() {
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
                            listaReceta.add(doc.document.toObject(RecetaDTO::class.java))
                        }
                    }
                    listaRecetaTemporal.addAll(listaReceta)
                    searchAdapter.notifyDataSetChanged()
                }
            })
    }


}