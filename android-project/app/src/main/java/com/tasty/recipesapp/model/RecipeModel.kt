package com.tasty.recipesapp.model

data class RecipeModel(
    val id: Int,                        // Corresponds to "recipeID"
    val name: String,                   // Corresponds to "name"
    val description: String,            // Corresponds to "description"
    val thumbnailUrl: String,           // Corresponds to "thumbnailUrl"
    val keywords: List<String>,         // Corresponds to "keywords" (could be a comma-separated list in the DTO, parsed into a list in the model)
    val isPublic: Boolean,              // Corresponds to "isPublic"
    val userEmail: String,              // Corresponds to "userEmail"
    val originalVideoUrl: String,       // Corresponds to "originalVideoUrl"
    val country: String,                // Corresponds to "country"
    val numServings: Int,               // Corresponds to "numServings"
    val components: List<ComponentModel>,  // List of components (needs to be mapped separately)
    val instructions: List<InstructionModel>,  // List of instructions (already handled)
)