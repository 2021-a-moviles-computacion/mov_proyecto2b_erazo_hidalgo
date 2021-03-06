package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferrifrancis.cookpad.R

import com.ferrifrancis.cookpad.adapter.MessageRecyclerAdapter
import com.ferrifrancis.cookpad.data.Data
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_message.*


class MessageFragment : Fragment() {
    private lateinit var homeAdapter: MessageRecyclerAdapter //es no null,pero se inicializará más luego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
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
        rv_message_frag_mess.apply {
            rv_message_frag_mess.layoutManager = LinearLayoutManager(activity)
            homeAdapter = MessageRecyclerAdapter()
            rv_message_frag_mess.adapter = homeAdapter
        }
    }

}