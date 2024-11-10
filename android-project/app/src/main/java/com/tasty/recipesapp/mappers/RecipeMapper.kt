package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.model.NutritionModel
import com.tasty.recipesapp.model.RecipeModel

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
        components = this.components.map { it.toModel() },  // Assuming there's a toModel() for components
        instructions = this.instructions.map { it.toModel() },  // Assuming there's a toModel() for instructions
    )
}