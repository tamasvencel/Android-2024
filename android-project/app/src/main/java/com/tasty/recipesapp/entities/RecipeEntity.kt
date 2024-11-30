package com.tasty.recipesapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val internalId: Long = 0L, // Room will handle generating this ID
    @SerializedName("json_data")
    val json: String
)

@Entity(tableName = "saved_recipe")
data class SavedRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val internalId: Long = 0L,
    @SerializedName("json_data")
    val json: String
)