package com.ferrifrancis.cookpad.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.ClasesParcelables.BIngrediente
import com.ferrifrancis.cookpad.R

class VerRecetaRecyclerAdapter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_receta_recycler_adapter)
        val ListaIngredientes = arrayListOf<BIngrediente>()

        val recyclerViewIngredientes= findViewById<RecyclerView>(
            R.id.rv_ingredientes
        )
        iniciarRecyclerView(
            ListaIngredientes,
            this,
            recyclerViewIngredientes
        )
    }

    fun iniciarRecyclerView(
        lista: List<BIngrediente>,
        actividad: VerRecetaRecyclerAdapter,
        recyclerView: RecyclerView
    ){
        val adaptador = IngredientesRecyclerAdapter(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter= adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager=androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }
}