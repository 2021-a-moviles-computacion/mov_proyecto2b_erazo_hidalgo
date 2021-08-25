package com.ferrifrancis.cookpad

class Home(
    var nombreAutorReceta: String,
    val imagenAutor : Int,
    val imagenReceta: Int,
    val tituloReceta: String,
    var reaccionAplauso: Int,
    var reaccionCorazon: Int,
    val comentario: String?

) {
}