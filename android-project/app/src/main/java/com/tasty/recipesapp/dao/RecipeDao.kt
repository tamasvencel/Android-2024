package com.tasty.recipesapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.entities.SavedRecipeEntity

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe WHERE internalId = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity?

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<RecipeEntity>

//    @Delete
//    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Insert
    suspend fun insertSavedRecipe(savedRecipe: SavedRecipeEntity): Long

    @Query("SELECT * FROM saved_recipe")
    suspend fun getSavedRecipes(): List<SavedRecipeEntity>

    @Query("SELECT * FROM saved_recipe WHERE internalId = :id")
    suspend fun getSavedRecipeById(id: Long): SavedRecipeEntity?

//    @Delete
//    suspend fun removeSavedRecipe(savedRecipe: SavedRecipeEntity)

    @Query("DELETE FROM saved_recipe WHERE internalId = :id")
    suspend fun removeSavedRecipe(id: Long)

    @Query("SELECT * FROM saved_recipe WHERE json = :json")
    suspend fun getSavedRecipeByJson(json: String): SavedRecipeEntity?
}