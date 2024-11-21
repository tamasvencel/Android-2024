package com.tasty.recipesapp.wrappers

import android.os.Parcel
import android.os.Parcelable
import com.tasty.recipesapp.model.InstructionModel

// A wrapper class to make instructions Parcelable
data class RecipeInstructionsParcelable(
    val instructions: List<InstructionModel>
) : Parcelable {

    // Constructor to read the data from the Parcel
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(InstructionModel.CREATOR) ?: emptyList()
    )

    // Write the properties to the Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(instructions)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RecipeInstructionsParcelable> {
        override fun createFromParcel(parcel: Parcel): RecipeInstructionsParcelable {
            return RecipeInstructionsParcelable(parcel)
        }

        override fun newArray(size: Int): Array<RecipeInstructionsParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
