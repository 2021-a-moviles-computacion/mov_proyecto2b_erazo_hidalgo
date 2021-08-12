package com.ferrifrancis.cookpad.data

import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R

class Data {
    companion object{
        fun createDataSetHome():ArrayList<Home> {
            //datos quemados para mostrar en el recycler view de home y search
            val list= ArrayList<Home>()
            list.add(Home("usuario1", R.drawable.usuario1,R.drawable.receta_fideos,
                "Fideos", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso"), "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"))
            list.add(Home("usuario2", R.drawable.usuario2,R.drawable.receta_lasagna,
                "Lasagna", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso"),"Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"))
            list.add(Home("usuario3", R.drawable.usuario3,R.drawable.donas,
                "Donas", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso"),"Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"))
            list.add(Home("usuario4", R.drawable.usuario4,R.drawable.hornado,
                "Hornado", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso"),"Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"))
            list.add(Home("usuario4", R.drawable.usuario1,R.drawable.hamburguesa,
                "Hamburguesa", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso"),"Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna. Juan 3:16"))
            return list
        }


    }
}