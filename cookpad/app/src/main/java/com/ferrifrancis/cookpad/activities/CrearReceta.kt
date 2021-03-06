package com.ferrifrancis.cookpad.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.*
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.ferrifrancis.cookpad.dto.UsuarioDTO
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_crear_receta.*
import kotlinx.android.synthetic.main.activity_registrarse.*

class CrearReceta : AppCompatActivity() {
    var usuario: UsuarioDTO?=null
    private lateinit var listaReceta: ArrayList<RecetaDTO>
    var Pos: Int = 0
    var receta: RecetaDTO?=null
    var uid_receta:String? =null

    var myUri = ""
    var imageUri: Uri? = null
    var storageRecetaImage: StorageReference? = null

    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var esFav: Int = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_receta)


        usuario= intent.getParcelableExtra<UsuarioDTO>("usuario")
        receta= intent.getParcelableExtra<RecetaDTO>("receta")

        storageRecetaImage = FirebaseStorage.getInstance().reference.child("Image Receta")

        setearUsuarioFirebase()
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
        val tituloReceta= findViewById<EditText>(R.id.et_titulo_truco1)
        val descripcionReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val procedenciaReceta = findViewById<EditText>(R.id.et_procedencia_receta)
        val comensales = findViewById<EditText>(R.id.ed_comensales_receta)
        val tiempoElaboracion = findViewById<EditText>(R.id.et_tiempo_receta)
        val paso1 = findViewById<EditText>(R.id.et_descripcion_paso1)
        val paso2 = findViewById<EditText>(R.id.et_descripcion_paso2)
        val paso3 = findViewById<EditText>(R.id.et_descripcion_paso3)
        val paso4 = findViewById<EditText>(R.id.et_descripcion_paso4)
        val Raplauso= findViewById<Chip>(R.id.chip_aplauso)
        val Rcorazon= findViewById<Chip>(R.id.chip_corazon)
        val imagen= findViewById<ImageView>(R.id.img_truco)


        val btnGuardarReceta = findViewById<Button>(R.id.btn_guardar_receta)
    //    btnGuardarReceta.isEnabled= (


    //            )
        btnGuardarReceta.setOnClickListener {
            if(

                tituloReceta.text.toString().isNotEmpty() &&
                descripcionReceta.text.toString().isNotEmpty()&&
                procedenciaReceta.text.toString().isNotEmpty()&&
                comensales.text.toString().isNotEmpty()&&
                tiempoElaboracion.text.toString().isNotEmpty()&&
                paso1.text.toString().isNotEmpty()&&
                paso2.text.toString().isNotEmpty()&&
                paso3.text.toString().isNotEmpty()&&
                paso4.text.toString().isNotEmpty()
            //  imagen.toString().isNotEmpty())
            )
            {
                Log.i("receta", "Entro if")
                crearReceta()

            }else{
                Log.i("receta", "Entro else")
                Toast.makeText(this, "Llene de manera correcta el formulario", Toast.LENGTH_LONG).show()
            }


        }

        val imagenReceta = findViewById<ImageView>(R.id.imagen_receta)
        imagenReceta.setOnClickListener {
         //   uploadImage()


            CropImage.activity()
                .setAspectRatio(2,1)
                .start(this@CrearReceta)
        }







    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null )
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            val imagenReceta= findViewById<ImageView>(R.id.imagen_receta)
            imagenReceta.setImageURI(imageUri)

        }

    }
    fun uploadImage(
        idReceta: String?
    ){
        when{
            imageUri == null ->Toast.makeText(this, "Seleccione una foto", Toast.LENGTH_LONG).show()

            else ->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("A??adir imagen receta")
                progressDialog.setMessage("Esta a??adiendo la foto")
                progressDialog.show()
                val fileRef = storageRecetaImage!!.child(idReceta +".jpg")
                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task->
                    if(!task.isSuccessful){
                        task.exception?.let {
                            throw  it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                })
                    .addOnCompleteListener(OnCompleteListener<Uri> { task->
                        if(task.isSuccessful){

                            val dowloadUrl = task.result
                            myUri= dowloadUrl.toString()
                            val ref = FirebaseDatabase.getInstance().reference.child("receta")
                            val postId= ref.push().key
                            Toast.makeText(this, "Account Information has been updated successfully.", Toast.LENGTH_LONG).show()


                            finish()
                            progressDialog.dismiss()

                        }
                        else{
                            progressDialog.dismiss()
                        }
                    })
            }
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
                        Log.i("firebase-firestore","Fall?? cargar usuario")
                    }

            }
        }
    }

    fun crearReceta(){

        val tituloReceta= findViewById<EditText>(R.id.et_titulo_truco1)
        val descripcionReceta = findViewById<EditText>(R.id.et_descripcion_receta)
        val procedenciaReceta = findViewById<EditText>(R.id.et_procedencia_receta)
        val comensales = findViewById<EditText>(R.id.ed_comensales_receta)
        val tiempoElaboracion = findViewById<EditText>(R.id.et_tiempo_receta)
        val paso1 = findViewById<EditText>(R.id.et_descripcion_paso1)
        val paso2 = findViewById<EditText>(R.id.et_descripcion_paso2)
        val paso3 = findViewById<EditText>(R.id.et_descripcion_paso3)
        val paso4 = findViewById<EditText>(R.id.et_descripcion_paso4)
        val Raplauso= findViewById<Chip>(R.id.chip_aplauso)
        val Rcorazon= findViewById<Chip>(R.id.chip_corazon)
        val imagen= findViewById<ImageView>(R.id.img_truco)
        val aplauso = 0
        val corazon = 0


        val objetoRecetaDTO = RecetaDTO(
            null,
            usuario!!.uid.toString(),
            tituloReceta.text.toString(),
            descripcionReceta.text.toString(),
            procedenciaReceta.text.toString(),
            comensales.text.toString().toInt(),
            tiempoElaboracion.text.toString(),
            paso1.text.toString(),
            paso2.text.toString(),
            paso3.text.toString(),
            paso4.text.toString(),
            0,
            0,
            usuario!!.nombre,



        )
        val nuevaReceta = hashMapOf<String, Any>(
            "uid_usuario" to objetoRecetaDTO.uid_usuario!!,
            "tituloReceta" to objetoRecetaDTO.tituloReceta!!,
            "descripcionReceta" to objetoRecetaDTO.descripcionReceta!!,
            "procedencia" to objetoRecetaDTO.procedenciaReceta!!,
            "comensales" to objetoRecetaDTO.comensales!!,
            "tiempoElaboracion" to objetoRecetaDTO.tiempoElaboracion!!,
            "paso1" to objetoRecetaDTO.paso1!!,
            "paso2" to objetoRecetaDTO.paso2!!,
            "paso3" to objetoRecetaDTO.paso3!!,
            "paso4" to objetoRecetaDTO.paso4!!,
            "reaccionAplauso" to objetoRecetaDTO.reaccionAplauso!!,
            "reaccionCorazon" to objetoRecetaDTO.reaccionCorazon!!,
            "nombreUsuarioAutor" to objetoRecetaDTO.nombreUsuarioAutor!!
            )
        val db= Firebase.firestore
        val referencia = db.collection("receta")
        referencia
            .add(nuevaReceta)
            .addOnSuccessListener {
                this.uid_receta = it.id
                crearIngredientes()
                uploadImage(uid_receta)
                limpiar()
                Toast.makeText(this, "Receta creada exitosamente", Toast.LENGTH_LONG).show()
                abrirActividadConParametros(MainActivity::class.java,usuario!!)
                Log.i("firestore","Se cre?? receta")
            }
            .addOnFailureListener{
                Log.i("firestore","NO Se cre?? receta")
            }



    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: UsuarioDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }







    fun crearIngredientes(){
        val ingrediente1= findViewById<EditText>(R.id.et_ingrediente_1).text.toString()
        val ingrediente2= findViewById<EditText>(R.id.et_ingrediente_2).text.toString()
        val ingrediente3= findViewById<EditText>(R.id.et_ingrediente_3).text.toString()
        val ingrediente4= findViewById<EditText>(R.id.et_ingrediente_4).text.toString()
        val ingrediente5= findViewById<EditText>(R.id.et_ingrediente_5).text.toString()
        val ingrediente6= findViewById<EditText>(R.id.et_ingrediente_6).text.toString()

        val cantidad1= findViewById<EditText>(R.id.tv_cantidadIngrediente1).text.toString()
        val cantidad2= findViewById<EditText>(R.id.tv_cantidadIngrediente2).text.toString()
        val cantidad3= findViewById<EditText>(R.id.tv_cantidadIngrediente3).text.toString()
        val cantidad4= findViewById<EditText>(R.id.tv_cantidadIngrediente4).text.toString()
        val cantidad5= findViewById<EditText>(R.id.tv_cantidadIngrediente5).text.toString()
        val cantidad6= findViewById<EditText>(R.id.tv_cantidadIngrediente6).text.toString()

        val nuevoIngrediente = hashMapOf<String, Any>(
            "receta_uid" to this.uid_receta!!,
            "cantidad1" to cantidad1,
            "ingrediente1" to ingrediente1 ,
            "cantidad2" to cantidad2,
            "ingrediente2" to ingrediente2 ,
            "cantidad3" to cantidad3,
            "ingrediente3" to ingrediente3 ,
            "cantidad4" to cantidad4,
            "ingrediente4" to ingrediente4,
            "cantidad5" to cantidad5,
            "ingrediente5" to ingrediente5,
            "cantidad6" to cantidad6,
            "ingrediente6" to ingrediente6,
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

        val tituloReceta= findViewById<EditText>(R.id.et_titulo_truco1)
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

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: RecetaDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("receta",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }







}