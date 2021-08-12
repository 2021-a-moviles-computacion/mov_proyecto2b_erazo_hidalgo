package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.AccountAdapter
import kotlinx.android.synthetic.main.fragment_account.*



class AccountFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        Log.i("fragment","Account creado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("fragment","on create view")
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        Log.i("fragment","on view created")
        setUpTabs()
    }

    private fun setUpTabs()
    {
        val adapter = fragmentManager?.let { AccountAdapter(supportFragmentManager= it) }
        adapter?.addFragment(TusTrucosFragment(),"Recetas guardadas ")
        adapter?.addFragment(TusRecetasFragment(),"Tus recetas")
        view_pager.adapter= adapter
        tabs.setupWithViewPager(view_pager)


    }


}