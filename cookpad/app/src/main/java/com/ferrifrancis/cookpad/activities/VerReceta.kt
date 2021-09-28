package com.ferrifrancis.cookpad.activities

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_ver_receta.*

class VerReceta : AppCompatActivity() {
    val PATH_IMAGE_RECETA= "Image Receta/"
    val PATH_IMAGE_USUARIO= "image_usuario/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_receta)

        val imagenReceta = findViewById<ImageView>(R.id.imageVerReceta)
        val imagenUsuario = findViewById<ImageView>(R.id.usuarioImagenVerReceta)

        val receta = intent.getParcelableExtra<RecetaDTO>("receta")

        jalaIngredientesFirestore(receta?.uid_receta)
        seteaDatosReceta(receta)

        if(receta?.uid_receta != null && receta?.uid_usuario != null) {

            val pathImagenReceta = PATH_IMAGE_RECETA + receta.uid_receta + ".jpg"
            val pathImageUsuario = PATH_IMAGE_USUARIO + receta.uid_usuario + ".jpg"

            cargarYMuestraImagen(pathImagenReceta, imagenReceta)
            cargarYMuestraImagen(pathImageUsuario, imagenUsuario)
        }



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

    fun seteaDatosReceta(recetaDTO: RecetaDTO?)
    {
        if(recetaDTO != null)
        {
            tituloVerReceta.text = recetaDTO.tituloReceta
            nombreUsuarioVerReceta.text = recetaDTO.nombreUsuarioAutor
            descripcion.text = recetaDTO.descripcionReceta
            tv_paso1.text = recetaDTO.paso1
            tv_paso2.text = recetaDTO.paso2
            tv_paso3.text = recetaDTO.paso3
            tv_paso4.text = recetaDTO.paso4
            tv_comensales.text= recetaDTO.comensales.toString()
            tv_tiempo_elaboracion.text = recetaDTO.tiempoElaboracion
            tv_ubicacion.text = recetaDTO.procedenciaReceta
        }
    }

    fun cargarYMuestraImagen(path: String?, imagen: ImageView?){
        if(path != null && imagen != null) {
            var referencia = Firebase.storage
            var nombreImg = referencia.reference.child(path)
            nombreImg.getBytes(10024 * 10024)
                .addOnSuccessListener {
                    val bit = BitmapFactory.decodeByteArray(it, 0, it.size)
                    //Log.i("Firebase-Imagen", "Imagen recuperada->  ${dataDir}" )
                    //var imagen = findViewById<ImageView>()
                      //  .setImageBitmap(bit)
                    imagen.setImageBitmap(bit)
                }
                .addOnFailureListener {

                }
        }
    }
}