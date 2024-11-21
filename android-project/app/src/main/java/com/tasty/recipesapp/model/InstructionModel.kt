package com.tasty.recipesapp.model

import android.os.Parcel
import android.os.Parcelable

data class InstructionModel(
    val id: Int,
    val displayText: String,
    val position: Int
)  : Parcelable {

    // Constructor to read the data from the Parcel (for retrieving from Bundle)
    constructor(parcel: Parcel) : this(
        parcel.readInt(), // Read id from Parcel
        parcel.readString() ?: "", // Read displayText from Parcel
        parcel.readInt() // Read position from Parcel
    )

    // Write the properties to the Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id) // Write id to Parcel
        parcel.writeString(displayText) // Write displayText to Parcel
        parcel.writeInt(position) // Write position to Parcel
    }

    // Standard Parcelable method (typically returns 0)
    override fun describeContents(): Int = 0

    // Companion object to create Parcelable instances
    companion object CREATOR : Parcelable.Creator<InstructionModel> {
        override fun createFromParcel(parcel: Parcel): InstructionModel {
            return InstructionModel(parcel) // Create from Parcel
        }

        override fun newArray(size: Int): Array<InstructionModel?> {
            return arrayOfNulls(size) // Create an empty array
        }
    }
}
