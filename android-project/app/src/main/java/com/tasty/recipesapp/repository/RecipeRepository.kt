package com.tasty.recipesapp.repository

import android.content.Context
import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.model.RecipeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import com.tasty.recipesapp.mappers.toModel

class RecipeRepository(private val context: Context) {
    fun getRecipes(): List<RecipeModel> {
        val recipeDTOs = readRecipesFromJson()
        return recipeDTOs.map { it.toModel() }
    }

    private fun readRecipesFromJson(): List<RecipeDTO> {
        val gson = Gson()
        var recipeList = listOf<RecipeDTO>()
        val assetManager = context.assets
        try {
            val inputStream = assetManager.open("more_recipes.json")
            val size = inputStream.available()
            val buffer =  ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            val type = object : TypeToken<List<RecipeDTO>>() {}.type
            recipeList = gson.fromJson(jsonString, type)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return recipeList
    }
}