package com.ferrifrancis.cookpad

class Home(
    val nombreAutorReceta: String,
    val imagenAutor : Int,
    val imagenReceta: Int,
    val tituloReceta: String,
    val reaccion: Map<Int?, String> = mapOf<Int?,String>(null to "caraFeliz", null to "corazon", null to "aplauso"),
    val comentario: String?
) {
}