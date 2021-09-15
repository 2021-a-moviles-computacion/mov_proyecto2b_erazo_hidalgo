package com.ferrifrancis.cookpad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import com.ferrifrancis.cookpad.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        val btnGuardarReceta = findViewById<Button>(R.id.btn_guardar_receta)
        btnGuardarReceta.setOnClickListener {
            crearReceta()
            crearIngredientes()
            Toast.makeText(this,"Se ha guardado la receta", Toast.LENGTH_SHORT).show()
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

    fun crearReceta(){
        val tituloReceta= findViewById<EditText>(R.id.et_titulo_receta)
        val descripcionReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val procedimientoReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val comensales = findViewById<EditText>(R.id.ed_comensales_receta)
        val tiempoElaboracion = findViewById<EditText>(R.id.et_tiempo_receta)
        val paso1 = findViewById<EditText>(R.id.et_descripcion_paso1)
        val paso2 = findViewById<EditText>(R.id.et_descripcion_paso2)
        val paso3 = findViewById<EditText>(R.id.et_descripcion_paso3)
        val paso4 = findViewById<EditText>(R.id.et_descripcion_paso4)
        val nuevaReceta = hashMapOf<String, Any>(
            "tituloReceta" to tituloReceta.text.toString(),
            "descripcionReceta" to descripcionReceta.text.toString(),
            "procedimientoReceta" to  procedimientoReceta.text.toString(),
            "comensales" to comensales.text.toString().toInt(),
            "tiempoElaboracion" to tiempoElaboracion.text.toString(),
            "paso1" to paso1.text.toString(),
            "paso2" to paso2.text.toString(),
            "paso3" to paso3.text.toString(),
            "paso4" to paso4.text.toString(),





            )
        val db= Firebase.firestore
        val referencia = db.collection("receta")
        referencia
            .add(nuevaReceta)
            .addOnSuccessListener {

                limpiar()
            }
            .addOnFailureListener{

            }



    }



    fun crearIngredientes(){
        val ingrediente1= findViewById<EditText>(R.id.et_ingrediente_1)
        val ingrediente2= findViewById<EditText>(R.id.et_ingrediente_2)
        val ingrediente3= findViewById<EditText>(R.id.et_ingrediente_3)
        val ingrediente4= findViewById<EditText>(R.id.et_ingrediente_4)
        val ingrediente5= findViewById<EditText>(R.id.et_ingrediente_5)
        val ingrediente6= findViewById<EditText>(R.id.et_ingrediente_6)

        val cantidad1= findViewById<TextView>(R.id.tv_cantidadIngrediente1)
        val cantidad2= findViewById<TextView>(R.id.tv_cantidadIngrediente2)
        val cantidad3= findViewById<TextView>(R.id.tv_cantidadIngrediente3)
        val cantidad4= findViewById<TextView>(R.id.tv_cantidadIngrediente4)
        val cantidad5= findViewById<TextView>(R.id.tv_cantidadIngrediente5)
        val cantidad6= findViewById<TextView>(R.id.tv_cantidadIngrediente6)
        val nuevoIngrediente = hashMapOf<String, Any>(
            "cantidad1" to cantidad1.text.toString(),
            "ingrediente1" to ingrediente1.text.toString(),
            "cantidad2" to cantidad2.text.toString(),
            "ingrediente2" to ingrediente2.text.toString(),
            "cantida3" to cantidad3.text.toString(),
            "ingrediente3" to ingrediente3.text.toString(),
            "cantidad4" to cantidad4.text.toString(),
            "ingrediente4" to ingrediente4.text.toString(),
            "cantidad5" to cantidad5.text.toString(),
            "ingrediente5" to ingrediente5.text.toString(),
            "cantidad6" to cantidad6.text.toString(),
            "ingrediente6" to ingrediente6.text.toString(),
        )

        val db = Firebase.firestore
        val referencia = db.collection("ingredientes")
        referencia
            .add(nuevoIngrediente)
            .addOnSuccessListener {
                limpiar()





            }
            .addOnFailureListener {  }

    }

    fun limpiar(){
        val ingrediente1= findViewById<EditText>(R.id.et_ingrediente_1)
        val ingrediente2= findViewById<EditText>(R.id.et_ingrediente_2)
        val ingrediente3= findViewById<EditText>(R.id.et_ingrediente_3)
        val ingrediente4= findViewById<EditText>(R.id.et_ingrediente_4)
        val ingrediente5= findViewById<EditText>(R.id.et_ingrediente_5)
        val ingrediente6= findViewById<EditText>(R.id.et_ingrediente_6)

        val cantidad1= findViewById<EditText>(R.id.tv_cantidadIngrediente1)
        val cantidad2= findViewById<EditText>(R.id.tv_cantidadIngrediente2)
        val cantidad3= findViewById<EditText>(R.id.tv_cantidadIngrediente3)
        val cantidad4= findViewById<EditText>(R.id.tv_cantidadIngrediente4)
        val cantidad5= findViewById<EditText>(R.id.tv_cantidadIngrediente5)
        val cantidad6= findViewById<EditText>(R.id.tv_cantidadIngrediente6)

        val tituloReceta= findViewById<EditText>(R.id.et_titulo_receta)
        val descripcionReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val procedimientoReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val comensales = findViewById<EditText>(R.id.ed_comensales_receta)
        val tiempoElaboracion = findViewById<EditText>(R.id.et_tiempo_receta)
        val paso1 = findViewById<EditText>(R.id.et_descripcion_paso1)
        val paso2 = findViewById<EditText>(R.id.et_descripcion_paso2)
        val paso3 = findViewById<EditText>(R.id.et_descripcion_paso3)
        val paso4 = findViewById<EditText>(R.id.et_descripcion_paso4)

        tituloReceta.text.clear()
        descripcionReceta.text.clear()
        procedimientoReceta.text.clear()
        comensales.text.clear()
        tiempoElaboracion.text.clear()
        paso1.text.clear()
        paso2.text.clear()
        paso3.text.clear()
        paso4.text.clear()
        ingrediente1.text.clear()
        ingrediente2.text.clear()
        ingrediente3.text.clear()
        ingrediente4.text.clear()
        ingrediente5.text.clear()
        ingrediente6.text.clear()
        cantidad1.text.clear()
        cantidad2.text.clear()
        cantidad3.text.clear()
        cantidad4.text.clear()
        cantidad5.text.clear()
        cantidad6.text.clear()








    }

}