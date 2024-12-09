package com.tasty.recipesapp.services

import com.tasty.recipesapp.model.RecipeModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("api/recipes")
    suspend fun getRecipes(): List<RecipeModel>

    @GET("api/recipes/{id}")
    suspend fun getRecipeDetail(@Path("id") id: Int): RecipeModel
}