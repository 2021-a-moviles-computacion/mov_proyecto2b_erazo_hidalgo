package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.Data
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_tus_trucos.*


class TusTrucosFragment : Fragment() {

    private lateinit var homeAdapter: HomeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tus_trucos, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initRecyclerView()
        addDataSet()

    }

    private fun addDataSet()
    {
        val data = Data.createDataSetHome()
        homeAdapter.submitList(data)
    }

    private fun initRecyclerView()
    {
        rv_tus_trucos.apply {
            rv_tus_trucos.layoutManager = GridLayoutManager(activity,2)
            //homeAdapter = HomeRecyclerAdapter()
            rv_tus_trucos.adapter = homeAdapter
        }
    }

}