package com.tasty.recipesapp.model

import java.io.Serializable

data class RecipeModel(
    val id: Long,                        // Corresponds to "recipeID"
    val name: String,                   // Corresponds to "name"
    val description: String,            // Corresponds to "description"
    val thumbnailUrl: String,           // Corresponds to "thumbnailUrl"
    val isPublic: Boolean,              // Corresponds to "isPublic"
    val userEmail: String,              // Corresponds to "userEmail"
    val originalVideoUrl: String,       // Corresponds to "originalVideoUrl"
    val country: String,                // Corresponds to "country"
    val numServings: Int,               // Corresponds to "numServings"
    val components: List<ComponentModel>,  // List of components (needs to be mapped separately)
    val instructions: List<InstructionModel>,  // List of instructions (already handled)
    var isFavorite: Boolean = false
) : Serializable