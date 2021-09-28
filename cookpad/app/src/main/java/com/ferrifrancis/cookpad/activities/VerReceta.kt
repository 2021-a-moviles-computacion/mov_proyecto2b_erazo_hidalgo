package com.ferrifrancis.cookpad.activities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ver_receta.*

class VerReceta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_receta)

        val recetaUID = intent.getStringExtra("receta")

        jalaRecetaFirestore(recetaUID)
        jalaIngredientesFirestore(recetaUID)

    }

    fun jalaIngredientesFirestore(uidReceta: String?)
    {
        if (uidReceta != null) {
            val db = Firebase.firestore

            db.collection("ingredientes")
                .whereEqualTo("receta_uid", uidReceta)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        et_cantidad_ingrediente.text=document.data.get("cantidad1").toString()
                        et_cantidad_ingrediente2.text=document.data.get("cantidad2").toString()
                        et_cantidad_ingrediente3.text=document.data.get("cantidad3").toString()
                        et_cantidad_ingrediente4.text=document.data.get("cantidad4").toString()
                        et_cantidad_ingrediente5.text=document.data.get("cantidad5").toString()
                        et_cantidad_ingrediente6.text=document.data.get("cantidad6").toString()
                        et_nombre_ingrediente.text=document.data.get("ingrediente1").toString()
                        et_nombre_ingrediente2.text=document.data.get("ingrediente2").toString()
                        et_nombre_ingrediente3.text=document.data.get("ingrediente3").toString()
                        et_nombre_ingrediente4.text=document.data.get("ingrediente4").toString()
                        et_nombre_ingrediente5.text=document.data.get("ingrediente5").toString()
                        et_nombre_ingrediente6.text=document.data.get("ingrediente6").toString()
                        //Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        }
    }

    fun jalaRecetaFirestore(uidReceta: String?) {
        if (uidReceta != null) {
            val db = Firebase.firestore
            val docRef = db.collection("receta").document(uidReceta)
            docRef.get()
                .addOnSuccessListener { doc ->
                    if (doc != null) {

                        val nombreUsuario: String? = doc.data?.get("nombreUsuarioAutor").toString()
                        val nombreReceta: String? = doc.data?.get("tituloReceta").toString()
                        val descripcionReceta: String? = doc.data?.get("descripcionReceta").toString()
                        val procedimientoReceta: String? = doc.data?.get("procedimientoReceta").toString()
                        val comensales: Int? = doc.data?.get("comensales").toString().toIntOrNull()
                        val tiempoElaboracion: String? = doc.data?.get("tiempoElaboracion").toString()
                        val paso1: String? = doc.data?.get("paso1").toString()
                        val paso2: String? = doc.data?.get("paso2").toString()
                        val paso3: String? = doc.data?.get("paso3").toString()
                        val paso4: String? = doc.data?.get("paso4").toString()

                        tituloVerReceta.text = nombreReceta
                        nombreUsuarioVerReceta.text = nombreUsuario
                        descripcion.text = descripcionReceta
                        tv_paso1.text = paso1
                        tv_paso2.text =paso2
                        tv_paso3.text = paso3
                        tv_paso4.text = paso4
                        tv_comensales.text= comensales.toString()
                        tv_tiempo_elaboracion.text = tiempoElaboracion


                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }
}