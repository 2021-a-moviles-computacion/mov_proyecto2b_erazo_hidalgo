package com.ferrifrancis.cookpad.dto

import android.os.Parcel
import android.os.Parcelable

class IngredienteDTO(
    var uid:String?,
    var uid_receta:String?,
    var ingrediente1: String?,
    var ingrediente2: String?,
    var ingrediente3: String?,
    var ingrediente4: String?,
    var ingrediente5: String?,
    var ingrediente6: String?,

    var cantidad1: String?,
    var cantidad2: String?,
    var cantidad3: String?,
    var cantidad4: String?,
    var cantidad5: String?,
    var cantidad6: String?,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(uid_receta)
        parcel.writeString(ingrediente1)
        parcel.writeString(ingrediente2)
        parcel.writeString(ingrediente3)
        parcel.writeString(ingrediente4)
        parcel.writeString(ingrediente5)
        parcel.writeString(ingrediente6)
        parcel.writeString(cantidad1)
        parcel.writeString(cantidad2)
        parcel.writeString(cantidad3)
        parcel.writeString(cantidad4)
        parcel.writeString(cantidad5)
        parcel.writeString(cantidad6)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IngredienteDTO> {
        override fun createFromParcel(parcel: Parcel): IngredienteDTO {
            return IngredienteDTO(parcel)
        }

        override fun newArray(size: Int): Array<IngredienteDTO?> {
            return arrayOfNulls(size)
        }
    }
}