package com.ferrifrancis.cookpad.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.data.PaisData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_registrarse.*

class RegistrarseActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var listaPaises: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_registrarse)

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
                                abrirActividad(MainActivity::class.java)
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
}