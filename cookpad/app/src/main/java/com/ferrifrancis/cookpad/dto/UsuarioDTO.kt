package com.ferrifrancis.cookpad.dto

import android.os.Parcel
import android.os.Parcelable

class UsuarioDTO(
    var uid: String?= null,
    var email: String?= null,
    var nombre: String?= null,
    var pais: String?= null,
    //var roles: ArrayList<String> = arrayListOf(),
    var sexo: String?=null

):Parcelable{
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
        parcel.writeString(uid)
        parcel.writeString(email)
        parcel.writeString(nombre)
        parcel.writeString(pais)
    //    parcel.writeString(roles.toString())
        parcel.writeString(sexo)
    }

    companion object CREATOR : Parcelable.Creator<UsuarioDTO> {
        override fun createFromParcel(parcel: Parcel): UsuarioDTO {
            return UsuarioDTO(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioDTO?> {
            return arrayOfNulls(size)
        }
    }
}