package com.ferrifrancis.cookpad.fragments

import android.os.Bundle

import android.util.Log
import android.view.ContextMenu

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.RecursiveAction


class HomeFragment : Fragment() {

    private lateinit var homeAdapter: HomeRecyclerAdapter
    private lateinit var recyclerView: RecyclerView//es no null,pero se inicializará más luego
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    private lateinit var  db: FirebaseFirestore

    private lateinit var listaHome: ArrayList<Home>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.rv_home)
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        recyclerView.setHasFixedSize(true)

        this.listaReceta = arrayListOf()

        this.homeAdapter = HomeRecyclerAdapter(this.listaReceta)

        recyclerView.adapter = this.homeAdapter

        eventChangeListener()

    //initRecyclerView()
        //addDataSet()
  
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
                           listaReceta.add(doc.document.toObject(RecetaDTO::class.java))
                        }
                    }
                    homeAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun addDataSet()
    {
        listaHome = Data.listaDatos
        homeAdapter.submitList(listaHome)
    }

    private fun initRecyclerView()
    {
        rv_home.apply {
            rv_home.layoutManager = GridLayoutManager(activity,2)
            //homeAdapter = HomeRecyclerAdapter()
            rv_home.adapter = homeAdapter

        }


        homeAdapter.setOnItemClickListener(object : HomeRecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int)
            {
                listaHome[position].nombreAutorReceta = "hola"
                Log.i("home-fragment","${listaHome[position].nombreAutorReceta }")
                Log.i("home-fragment","posición seleccionada ${position}")
            }
        })

    }
}