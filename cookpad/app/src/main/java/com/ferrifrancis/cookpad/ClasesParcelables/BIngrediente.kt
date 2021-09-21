package com.ferrifrancis.cookpad.ClasesParcelables

import android.os.Parcel
import android.os.Parcelable

class BIngrediente(

    val cantidadIngrediente: String?,
    val nombreIngrediente: String?,
    val imgOpciones: Int,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),

    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel?, flag: Int) {
        parcel?.writeString(cantidadIngrediente)
        parcel?.writeString(nombreIngrediente)

        parcel?.writeInt(imgOpciones)

    }

    companion object CREATOR : Parcelable.Creator<BIngrediente> {
        override fun createFromParcel(parcel: Parcel): BIngrediente {
            return BIngrediente(parcel)
        }

        override fun newArray(size: Int): Array<BIngrediente?> {
            return arrayOfNulls(size)
        }
    }
}