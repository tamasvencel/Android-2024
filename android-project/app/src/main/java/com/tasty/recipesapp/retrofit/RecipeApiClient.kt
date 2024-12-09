package com.tasty.recipesapp.retrofit

import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.services.RecipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiClient {
    companion object {
        private const val BASE_URL =
            "https://recipe-appservice-cthjbdfafnhfdtes.germanywestcentral-01.azurewebsites.net/"
    }

    private val recipeService: RecipeService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        recipeService = retrofit.create(RecipeService::class.java)
    }

    suspend fun getRecipes(): List<RecipeModel>? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.getRecipes()
            } catch (e: Exception) {
                // Handle exceptions here
                null
            }
        }
    }

    suspend fun getRecipeDetail(id: Int): RecipeModel? {
        return withContext(Dispatchers.IO) {
            try {
                recipeService.getRecipeDetail(id)
            } catch (e: Exception) {
                null
            }
        }
    }
}