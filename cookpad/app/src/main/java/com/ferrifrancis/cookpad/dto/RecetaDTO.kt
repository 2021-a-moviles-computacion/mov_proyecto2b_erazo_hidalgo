package com.ferrifrancis.cookpad.dto

import android.os.Parcel
import android.os.Parcelable

class RecetaDTO(
    var uid: String?,
    var uid_usuario:String?,
    var tituloReceta: String?,
    var descripcionReceta: String?,
    var procedimientoReceta: String?,
    var comensales: Int?,
    var tiempoElaboracion: String?,
    var paso1: String?,
    var paso2: String?,
    var paso3: String?,
    var paso4: String?,
    var reaccionAplauso: Int?,
    var reaccionCorazon: Int?

):Parcelable {
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

        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(uid_usuario)
        parcel.writeString(tituloReceta)
        parcel.writeString(descripcionReceta)
        parcel.writeString(procedimientoReceta)
        parcel.writeInt(comensales!!)
        parcel.writeString(tiempoElaboracion)
        parcel.writeString(paso1)
        parcel.writeString(paso2)
        parcel.writeString(paso3)
        parcel.writeString(paso4)
        parcel.writeInt(reaccionAplauso!!)

        parcel.writeInt(reaccionCorazon!!)
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



