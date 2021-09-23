package com.ferrifrancis.cookpad.dto

import android.os.Parcel
import android.os.Parcelable

class RecetaDTO(
    var uid_receta: String? = null,
    var uid_usuario:String?= null,
    var tituloReceta: String?= null,
    var descripcionReceta: String?= null,
    var procedimientoReceta: String?= null,
    var comensales: Int?= null,
    var tiempoElaboracion: String?= null,
    var paso1: String?= null,
    var paso2: String?= null,
    var paso3: String?= null,
    var paso4: String?= null,
    var reaccionAplauso: Int?= null,
    var reaccionCorazon: Int?= null,
    val nombreUsuarioAutor: String?= null,
    val imageReceta: String? = null

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid_receta)
        parcel.writeString(uid_usuario)
        parcel.writeString(tituloReceta)
        parcel.writeString(descripcionReceta)
        parcel.writeString(procedimientoReceta)
        parcel.writeValue(comensales)
        parcel.writeString(tiempoElaboracion)
        parcel.writeString(paso1)
        parcel.writeString(paso2)
        parcel.writeString(paso3)
        parcel.writeString(paso4)
        parcel.writeValue(reaccionAplauso)
        parcel.writeValue(reaccionCorazon)
        parcel.writeString(nombreUsuarioAutor)
        parcel.writeString(imageReceta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecetaDTO> {
        override fun createFromParcel(parcel: Parcel): RecetaDTO {
            return RecetaDTO(parcel)
        }

        override fun newArray(size: Int): Array<RecetaDTO?> {
            return arrayOfNulls(size)
        }
    }
}