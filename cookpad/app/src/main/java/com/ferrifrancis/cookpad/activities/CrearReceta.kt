package com.ferrifrancis.cookpad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.PopupMenu
import com.ferrifrancis.cookpad.R

class CrearReceta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_receta)

        // calling the action bar
        val actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.title=""
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        val dotMenuIngr1 = findViewById<ImageView>(R.id.img_three_dots_menu_secc_ingr)
        val dotMenuIngr2 = findViewById<ImageView>(R.id.img_three_dots_menu2_secc_ingr)
        val dotMenuPasos = findViewById<ImageView>(R.id.img_three_dots_menu_secc_pasos)

        dotMenuIngr1.setOnClickListener {
            setPopupMenuIngrediente(dotMenuIngr1)
        }

        dotMenuIngr2.setOnClickListener {
            setPopupMenuIngrediente(dotMenuIngr2)
        }

        dotMenuPasos.setOnClickListener {
            setPopupMenuPaso(dotMenuPasos)
        }
    }

    fun setPopupMenuIngrediente(imagen: ImageView)
    {
        val popupMenu = PopupMenu(this, imagen)
        popupMenu.inflate(R.menu.menu_add_ingrediente)
        popupMenu.show()
    }

    fun setPopupMenuPaso(imagen: ImageView)
    {
        val popupMenu = PopupMenu(this, imagen)
        popupMenu.inflate(R.menu.menu_add_paso)
        popupMenu.show()
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