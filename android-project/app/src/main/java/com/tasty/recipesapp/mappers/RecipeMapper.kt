package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.model.NutritionModel
import com.tasty.recipesapp.model.RecipeModel
import com.google.gson.Gson

fun RecipeDTO.toModel(): RecipeModel {
    return RecipeModel(
        id = this.recipeID,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        keywords = this.keywords.split(",").map { it.trim() },  // Convert comma-separated keywords to a list
        isPublic = this.isPublic,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings,
        components = this.components.map { it.toModel() },
        instructions = this.instructions.map { it.toModel() },
    )
}

// Convert RecipeEntity to RecipeModel (for Room database conversion)
fun RecipeEntity.toModel(): RecipeModel {
    val gson = Gson()
    return gson.fromJson(this.json, RecipeModel::class.java)
}

// Convert RecipeModel to RecipeEntity (for Room database storage)
fun RecipeModel.toEntity(): RecipeEntity {
    val gson = Gson()
    return RecipeEntity(
        json = gson.toJson(this)
    )
}