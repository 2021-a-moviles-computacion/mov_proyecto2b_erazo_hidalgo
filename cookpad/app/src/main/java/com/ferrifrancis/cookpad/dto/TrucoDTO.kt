package com.ferrifrancis.cookpad.dto

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

class TrucoDTO(
    var uid_truco: String? = null,
    var tituloTruco: String? =null,
    var descripcionTruco: String?=null,
    var idUsuario: String?=null,
    var nombreUsuarioAutor: String?= null,
    var imageTruco: Bitmap? = null,
    var imageUsuario: Bitmap? = null

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tituloTruco)
        parcel.writeString(descripcionTruco)
        parcel.writeString(idUsuario)
        parcel.writeString(uid_truco)
        parcel.writeString(nombreUsuarioAutor)
    }

    companion object CREATOR : Parcelable.Creator<TrucoDTO> {
        override fun createFromParcel(parcel: Parcel): TrucoDTO {
            return TrucoDTO(parcel)
        }

        override fun newArray(size: Int): Array<TrucoDTO?> {
            return arrayOfNulls(size)
        }
    }
}