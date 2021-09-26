package com.ferrifrancis.cookpad.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.ferrifrancis.cookpad.dto.TrucoDTO
import com.ferrifrancis.cookpad.dto.UsuarioDTO
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage

class CrearTruco : AppCompatActivity() {
    var uid_truco:String? =null
    var usuario: UsuarioDTO?=null
    var myUri = ""
    var imageUri: Uri? = null
    var storageRecetaImage: StorageReference? = null
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_truco2)
        usuario= intent.getParcelableExtra<UsuarioDTO>("usuario")
        storageRecetaImage = FirebaseStorage.getInstance().reference.child("Image Truco")
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
        val imagenTruco= findViewById<ImageView>(R.id.img_truco)
        imagenTruco.setOnClickListener{
            uploadImage(this.uid_truco)
            CropImage.activity()
                .setAspectRatio(2,1)
                .start(this@CrearTruco)
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

        val objetoTrucoDTO = TrucoDTO(
            null,
            titulo,
            truco ,
            usuario!!.uid.toString(),
            usuario!!.nombre,



            )

        val nuevoTruco = hashMapOf<String, Any>(
            "idUsuario" to objetoTrucoDTO.idUsuario!!,
            "tituloTruco" to objetoTrucoDTO.tituloTruco!!,
            "descripcionTruco" to objetoTrucoDTO.descripcionTruco!!,
            "nombreUsuarioAutor" to objetoTrucoDTO.nombreUsuarioAutor!!

        )

        val db = Firebase.firestore
        val trucoColeccion = db.collection("truco")
        trucoColeccion.add(nuevoTruco)
            .addOnSuccessListener {
                this.uid_truco = it.id
                uploadImage(uid_truco)
                abrirActividadConParametros(MainActivity::class.java,usuario!!)
                Log.i("firestore","Se creó truco")
            }
            .addOnFailureListener {
                Log.i("firestore","No se creó truco")
            }
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: UsuarioDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null )
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            val imagenTruco= findViewById<ImageView>(R.id.img_truco)
            imagenTruco.setImageURI(imageUri)

        }

    }
    fun uploadImage(
        idTruco: String?
    ){


        when{
            imageUri == null -> Toast.makeText(this, "Seleccione una foto", Toast.LENGTH_LONG).show()

            else ->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Añadir imagen truco")
                progressDialog.setMessage("Esta añadiendo la foto")
                progressDialog.show()
                val fileRef = storageRecetaImage!!.child(idTruco+".jpg")
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

                            val ref = FirebaseDatabase.getInstance().reference.child("truco")
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

}