package com.ferrifrancis.cookpad.data

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.dto.RecetaDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Data {

    companion object {
        var listaDatos: ArrayList<Home> = createDataSetHome()
        val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401




        fun createDataSetHome(): ArrayList<Home> {
            //datos quemados para mostrar en el recycler view de home y search
            val list = ArrayList<Home>()
            list.add(
                Home(
                    "usuario1",
                    R.drawable.usuario1,
                    R.drawable.receta_fideos,
                    "Fideos",
                    0,
                    0,
                    "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"
                )
            )
            list.add(
                Home(
                    "usuario2",
                    R.drawable.usuario2,
                    R.drawable.receta_lasagna,
                    "Lasagna",
                    1,
                    1,
                    "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"
                )
            )
            list.add(
                Home(
                    "usuario3",
                    R.drawable.usuario3,
                    R.drawable.donas,
                    "Donas",
                    2,
                    2,
                    "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"
                )
            )
            list.add(
                Home(
                    "usuario4",
                    R.drawable.usuario4,
                    R.drawable.hornado,
                    "Hornado",
                    3,
                    3,
                    "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"
                )
            )
            list.add(
                Home(
                    "usuario1",
                    R.drawable.usuario1,
                    R.drawable.hamburguesa,
                    "Hamburguesa",
                    0,
                    0,
                    "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"
                )
            )
            return list
        }

        fun aumentarEn1Reaccion(indxDato: Int, tipoReaccion: Int) {
            //tipoReaccion 0-> aplauso, 1 -> corazon
            when (tipoReaccion) {
                0 -> {
                    val aplausoNum: Int = listaDatos[indxDato].reaccionAplauso
                    listaDatos[indxDato].reaccionAplauso = aplausoNum + 1

                }
                1 -> {
                    val corazonNum: Int = listaDatos[indxDato].reaccionCorazon
                    listaDatos[indxDato].reaccionCorazon = corazonNum + 1

                }

            }
        }








    }


}