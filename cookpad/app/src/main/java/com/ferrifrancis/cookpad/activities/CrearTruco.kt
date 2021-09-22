package com.ferrifrancis.cookpad.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.UsuarioDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearTruco : AppCompatActivity() {

    var usuario: UsuarioDTO?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_truco2)

        setearUsuarioFirebase()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.im_publicar ->{
                abrirActividad(MainActivity::class.java)
                crearTrucoFirestore()
                return true
            }
        }



        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar_crear_receta, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun crearTrucoFirestore()
    {
        val titulo = findViewById<EditText>(R.id.et_titulo_truco).text.toString()
        val truco = findViewById<EditText>(R.id.et_descripcion_truco).text.toString()

        val nuevoTruco = hashMapOf<String, Any>(
            "tituloTruco" to titulo,
            "descripcionTruco" to truco,
            "idUsuario" to this.usuario?.uid!!
        )

        val db = Firebase.firestore
        val trucoColeccion = db.collection("truco")
        trucoColeccion.add(nuevoTruco)
            .addOnSuccessListener {
                Log.i("firestore","Se creó truco")
            }
            .addOnFailureListener {
                Log.i("firestore","No se creó truco")
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

    fun setearUsuarioFirebase()
    {
        val instanciaAuth = FirebaseAuth.getInstance()//usuario que actualmente estalogead
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal !=null)
        {
            if(usuarioLocal.email != null)
            {
                val db = Firebase.firestore
                val referencia = db. collection("usuario") //de la coleccin usuario
                    .document(usuarioLocal.email.toString()) //deme el doc con este email

                referencia.get()
                    .addOnSuccessListener {
                        val usuarioCargado = it.toObject(UsuarioDTO::class.java)

                        Log.i("firebase-firestore", "Usuario cargado")
                        if(usuarioCargado !=null){
                            this.usuario = UsuarioDTO(
                                usuarioCargado.uid,
                                usuarioCargado.email,
                                usuarioCargado.nombre,
                                usuarioCargado.pais,
                                usuarioCargado.sexo
                                // usuarioCargado.roles

                            )
                            Log.i("firebase-firestore", "${usuarioCargado.nombre}")
                        }
                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore","Falló cargar usuario")
                    }

            }
        }
    }
}