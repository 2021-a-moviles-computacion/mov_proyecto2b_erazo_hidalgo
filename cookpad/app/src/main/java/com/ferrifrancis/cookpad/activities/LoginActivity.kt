package com.ferrifrancis.cookpad.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ferrifrancis.cookpad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registrarse.*


class LoginActivity : AppCompatActivity() {

    val CODIGO_INICIO_SESION = 102


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //llamarLoginUsuario()

        val botonRegistrarse = findViewById<Button>(R.id.btn_registrarse)
        val botonIniciarSesion = findViewById<Button>(R.id.btn_iniciar_sesion)


        botonIniciarSesion.setOnClickListener {
            val email = et_email.text.toString()
            val contrasenia = et_constrasenia.text.toString()

            if (!email.isNullOrEmpty() && !contrasenia.isNullOrEmpty())
            {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contrasenia)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Usuario logeado exitosamente", Toast.LENGTH_SHORT).show()

                            abrirActividad(MainActivity::class.java)
                        }
                        else
                        {
                            Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else
            {
                Toast.makeText(this,"Por favor llene los campos", Toast.LENGTH_SHORT).show()
            }
        }

        botonRegistrarse.setOnClickListener {
            abrirActividad(RegistrarseActivity::class.java)
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        /*when (requestCode) {
            CODIGO_INICIO_SESION -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario: IdpResponse? = IdpResponse.fromResultIntent(data)
                    if(usuario != null) {
                        abrirActividad(MainActivity::class.java)
                        if (usuario?.isNewUser == true) {

                            Log.i("firebase-login", "Nuevo Usuario")

                        } else {
                            Log.i("firebase-login", "Usuario Antiguo")
                        }
                    }
                } else {
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }*/
    }




    fun abrirActividad(clase: Class<*>) {
        //para que se abra la página debo hacer un intent
        val intentExplicito = Intent(
            this,
            clase
        )
        //startActivity para que aparezca la nueva actividad
        this.startActivity(intentExplicito)
    }
    /*
    fun llamarLoginUsuario() {
        val providers = arrayListOf(
            // lista de los proveedores
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build(),
            CODIGO_INICIO_SESION
        )
    }
    */

}