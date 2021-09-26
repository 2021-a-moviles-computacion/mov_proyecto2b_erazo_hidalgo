package com.ferrifrancis.cookpad.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.PaisData
import com.ferrifrancis.cookpad.dto.UsuarioDTO
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registrarse.*

class RegistrarseActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var listaPaises: Spinner
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var usuario: UsuarioDTO?= null

    var myUri = ""
    var imageUri: Uri? = null
    var storageRecetaImage: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_registrarse)

        storageRecetaImage = FirebaseStorage.getInstance().reference.child("image_usuario")

        val botonRegistrarse = findViewById<Button>(R.id.btn_registrarse)
        listaPaises = findViewById<Spinner>(R.id.spinner)

        listaPaises.adapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,PaisData.paisesDataSet())


        botonRegistrarse.setOnClickListener {
            val email = et_email_registro.text.toString()
            val contrasenia = et_contrasenia_registro.text.toString()

            if (!email.isNullOrEmpty() && !contrasenia.isNullOrEmpty())
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contrasenia)
                    .addOnCompleteListener(
                        { task ->
                            if (task.isSuccessful)
                            {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(this,"Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                                val usuarioLogeado: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
                                registrarUsuarioEnColeccion(usuarioLogeado)
                                if (usuarioLogeado != null) {
                                    Log.i("login","entró usuario logeado")
                                    uploadImage(usuarioLogeado.uid)
                                }
                                Log.i("usuario","${usuarioLogeado}")
                                abrirActividadConParametros(MainActivity::class.java, this.usuario!!)

                            }
                            else
                            {
                                Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
            }
            else
            {
                Toast.makeText(this,"Por favor llene los campos", Toast.LENGTH_SHORT).show()
            }

        }

        val imagenUsuario = findViewById<CircleImageView>(R.id.img_usuario)
        imagenUsuario.setOnClickListener{
            CropImage.activity()
                .setAspectRatio(2,1)
                .start(this@RegistrarseActivity)
        }

    }

    //pone la imagen seleccionada en el imageview
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null )
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            val imagenTruco= findViewById<ImageView>(R.id.img_usuario)
            imagenTruco.setImageURI(imageUri)

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

    fun registrarUsuarioEnColeccion(usuarioLogeado: FirebaseUser?)
    {
        val db= Firebase.firestore

        val uid: String? = usuarioLogeado?.uid
        val email: String? = usuarioLogeado?.email
        val nombre: String? = et_nombre_usuario_registro.text.toString()
        val sexo: String? = findViewById<RadioButton>(rg_sexo.checkedRadioButtonId).text.toString()
        val rolesUsuario = arrayListOf("usuario")
        val pais: String? = listaPaises.getSelectedItem().toString()

        Log.i("firebase-firestore","funcoin registrar usuario coleccion")
        Log.i("firebase-firestore","pais seleccionado: ${pais}")

        if(uid != null && email != null && nombre != null && sexo != null && pais != null) {
            Log.i("firebase-firestore","todos los campos no son nulos")
            this.usuario= UsuarioDTO(
                uid,
                email,
                nombre,
                pais,
                sexo,
             //   roles
            )
            val nuevoUsuario = hashMapOf<String, Any>(
                "uid" to uid,
                "email" to email,
                "nombre" to nombre,
                "pais" to pais,
                "sexo" to sexo,
                "roles" to rolesUsuario
            )

            db.collection("usuario")
                .document(email)
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    Log.i("firebase-firestore","Se registró usuario en la colección")

                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","No se registró usuario en la colección")
                }

        }
        else
        {
            Log.i("firebase-login","ERROR")
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

    fun uploadImage(
        idUsuario: String?
    ){


        when{
            imageUri == null -> Toast.makeText(this, "Seleccione una foto", Toast.LENGTH_LONG).show()

            else ->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Añadir imagen truco")
                progressDialog.setMessage("Esta añadiendo la foto")
                progressDialog.show()
                val fileRef = storageRecetaImage!!.child(idUsuario+".jpg")
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
                            Log.i("login","se cargó imagen")

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