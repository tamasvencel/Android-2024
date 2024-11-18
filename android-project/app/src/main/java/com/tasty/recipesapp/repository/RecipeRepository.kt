package com.tasty.recipesapp.repository

import android.content.Context
import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.model.RecipeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.mappers.toEntity
import java.io.IOException
import com.tasty.recipesapp.mappers.toModel
import org.json.JSONObject

class RecipeRepository(private val context: Context,  private val recipeDao: RecipeDao) {
    // Get all recipes from the Room database
    suspend fun getAllRecipes(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map {
            val jsonObject = JSONObject(it.json)
            jsonObject.apply { put("id", it.internalId) }
            val gson = Gson()
            gson.fromJson(jsonObject.toString(), RecipeDTO::class.java).toModel()
        }
    }

    // Add a new recipe to the database
    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insertRecipe(recipe)
    }

    // Delete a recipe from the database
    suspend fun deleteRecipe(recipe: RecipeEntity) {
        // Delete from Room Database
        recipeDao.deleteRecipe(recipe)
    }

    // Fetch a single recipe by its ID
    suspend fun getRecipeById(id: Long): RecipeModel? {
        // Fetch recipe entity from Room Database
        val recipeEntity = recipeDao.getRecipeById(id)

        return recipeEntity?.toModel()  // Now this works!
    }

    // Read recipes from a local JSON file (optional, for initialization or fallback)
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