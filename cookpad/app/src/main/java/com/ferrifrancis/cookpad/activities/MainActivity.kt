package com.ferrifrancis.cookpad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   // private val accountFragment = AccountFragment()
   private val trucoFragment = TrucoFragment()
    private val addFragment = AddFragment()
    private val homeFragment = HomeFragment()
    private val messageFragment = MessageFragment()
    private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)



        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {replaceFragment(homeFragment)
                    Log.i("fragment","selecciono icono home")}
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_add -> replaceFragment(addFragment)
                R.id.ic_message -> replaceFragment(messageFragment)
                R.id.ic_account -> replaceFragment(trucoFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment)
    {
        if (fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            Log.i("fragment","ingreso a la funcion replaceFragment")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }
}