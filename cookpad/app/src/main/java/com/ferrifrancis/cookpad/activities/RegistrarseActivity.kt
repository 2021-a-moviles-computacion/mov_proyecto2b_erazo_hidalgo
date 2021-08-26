package com.ferrifrancis.cookpad.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.cookpad.R

class RegistrarseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        val botonRegistrarse = findViewById<Button>(R.id.btn_registrarse)

        botonRegistrarse.setOnClickListener {
            abrirActividad(MainActivity::class.java)
        }
    }

    fun abrirActividad(clase : Class<*>)
    {
        //para que se abra la página debo hacer un intent
        val intentExplicito = Intent(
            this,
            clase
        )
        //startActivity para que aparezca la nueva actividad
        this.startActivity(intentExplicito)
    }
}