package com.tasty.recipesapp.repository

import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.model.RecipeModel

class RecipeRepository() {
    fun getRecipes(): List<RecipeModel> {
        val recipeDTOs = readRecipesFromJson()
        return recipeDTOs.map { it.toModel() }
    }

    private fun readRecipesFromJson(): List<RecipeDTO> {
        val gson = Gson()
    }
}