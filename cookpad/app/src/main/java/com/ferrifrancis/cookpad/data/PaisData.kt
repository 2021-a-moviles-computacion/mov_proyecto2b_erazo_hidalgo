package com.ferrifrancis.cookpad.data

import com.ferrifrancis.cookpad.Home

class PaisData {
    companion object {

        fun paisesDataSet():ArrayList<String> {
            val paises = arrayListOf<String>(
                "Argentina","Bolivia", "Brasil","Chile","Colombia","Ecuador","Guyana",
                "Guyana Francesa","Paraguay", "Perú","Suriname","Uruguay","Venezuela"
            )
            return paises
        }
    }
}