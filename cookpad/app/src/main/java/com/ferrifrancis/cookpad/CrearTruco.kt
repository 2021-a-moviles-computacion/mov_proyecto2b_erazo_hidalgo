package com.ferrifrancis.cookpad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.PopupMenu

class CrearTruco : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_truco2)

        val actionBar = getSupportActionBar()
        if (actionBar != null)
        {
            actionBar.title=""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        val imgBoton = findViewById<ImageView>(R.id.img_three_dots_menu2_truco)
        imgBoton.setOnClickListener {
            val popupMenu = PopupMenu(this, imgBoton)
            popupMenu.inflate(R.menu.menu_add_paso)
            popupMenu.show()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar_crear_receta, menu)
        return super.onCreateOptionsMenu(menu)
    }
}