package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.model.NutritionModel
import com.tasty.recipesapp.model.RecipeModel
import com.google.gson.Gson
import com.tasty.recipesapp.entities.SavedRecipeEntity

fun RecipeDTO.toModel(): RecipeModel {
    return RecipeModel(
        id = this.recipeID,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        isPublic = this.isPublic,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings,
        components = this.components.map { it.toModel() },
        instructions = this.instructions?.toModelList() ?: emptyList(),
        isFavorite = this.isFavorite
    )
}

fun List<RecipeDTO>.toModelList(): List<RecipeModel> {
    return this.map { it.toModel() }
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
        this.id.toLong(),
        json = gson.toJson(this)
    )
}

fun RecipeModel.toSavedRecipeEntity(): SavedRecipeEntity {
    val gson = Gson()
    return SavedRecipeEntity(
        this.id.toLong(),
        json = gson.toJson(this)
    )
}

fun SavedRecipeEntity.fromSavedRecipeEntity(): RecipeModel {
    val gson = Gson()
    return gson.fromJson(this.json, RecipeModel::class.java)
}

fun SavedRecipeEntity.fromEntity(): RecipeModel {
    val gson = Gson()
    return gson.fromJson(this.json, RecipeModel::class.java)
}