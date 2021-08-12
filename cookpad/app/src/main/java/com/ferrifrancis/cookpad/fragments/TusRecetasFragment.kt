package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.adapter.AccountTusRecetasRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_tus_recetas.*

class TusRecetasFragment : Fragment() {

    private lateinit var tusRecetasAdapter: AccountTusRecetasRecyclerAdapter //es no null,pero se inicializará más luego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tus_recetas, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initRecyclerView()
        addDataSet()

    }

    private fun addDataSet()
    {
        val data = Data.createDataSetHome()
        tusRecetasAdapter.submitList(data)
    }

    private fun initRecyclerView()
    {
        rv_tus_recetas.apply {
            rv_tus_recetas.layoutManager = GridLayoutManager(activity,2)
            tusRecetasAdapter = AccountTusRecetasRecyclerAdapter()
            rv_tus_recetas.adapter = tusRecetasAdapter
        }
    }

}