package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.entities.RecipeEntity
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
        components = this.components.map { it.toModel() },
        instructions = this.instructions.map { it.toModel() },
    )
}

// Convert RecipeEntity to RecipeModel (for Room database conversion)
fun RecipeEntity.toModel(): RecipeModel {
    return RecipeModel(
        id = this.internalId,  // Use internalId as the id
        name = this.name,  // Non-nullable name
        description = this.description ?: "",  // Default to empty string if null
        thumbnailUrl = this.thumbnailUrl ?: "",  // Default to empty string if null
        keywords = this.keywords.split(",").map { it.trim() },  // Convert comma-separated keywords to a list
        isPublic = this.isPublic,
        userEmail = this.userEmail ?: "",  // Default to empty string if null
        originalVideoUrl = this.originalVideoUrl ?: "",  // Default to empty string if null
        country = this.country ?: "",  // Default to empty string if null
        numServings = this.numServings ?: 0,  // Default to 0 if null
        components = this.components,  // Convert components string to list
        instructions = this.instructions // Convert instructions string to list
    )
}

// Convert RecipeModel to RecipeEntity (for Room database storage)
fun RecipeModel.toEntity(): RecipeEntity {
    return RecipeEntity(
        internalId = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        keywords = this.keywords.joinToString(", "),  // Join list back to a string for Room
        isPublic = this.isPublic,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings,
        components = this.components,  // Convert list to comma-separated string
        instructions = this.instructions // Convert list to comma-separated string
    )
}