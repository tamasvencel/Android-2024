package com.tasty.recipesapp.repository

import android.content.Context
import android.util.Log
import com.tasty.recipesapp.dto.RecipeDTO
import com.tasty.recipesapp.model.RecipeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.entities.SavedRecipeEntity
import com.tasty.recipesapp.mappers.fromSavedRecipeEntity
import com.tasty.recipesapp.mappers.toEntity
import java.io.IOException
import com.tasty.recipesapp.mappers.toModel
import com.tasty.recipesapp.mappers.toModelList
import com.tasty.recipesapp.mappers.toSavedRecipeEntity
import org.json.JSONObject

class RecipeRepository(private val recipeDao: RecipeDao) {
    private var recipes: List<RecipeModel>? = null

    // Get all recipes from the Room database
    private fun getAll(): List<RecipeModel> {
        return readRecipesFromJson().toModelList()
    }

    fun getRecipes(): List<RecipeModel> {
        if (recipes == null) {
            recipes = getAll()
        }

        return recipes ?: emptyList()
    }

    suspend fun getSavedRecipes(): List<RecipeModel> {
        return recipeDao.getSavedRecipes().map { savedRecipeEntity ->
            savedRecipeEntity.fromSavedRecipeEntity()  // Convert each SavedRecipeEntity to Recipe
        }
    }

    // Function to toggle the saved state of a recipe
    suspend fun toggleSavedRecipe(recipe: RecipeModel) {
        val savedRecipe = recipe.toSavedRecipeEntity()
        Log.i("RECIPE", "Saved recipe: $savedRecipe")
        if (recipeDao.getSavedRecipeById(savedRecipe.internalId) == null) {
            Log.i("RECIPE", "Inserting saved recipe")
            insertSavedRecipe(savedRecipe)
        } else {
            removeSavedRecipe(savedRecipe)
            Log.i("RECIPE", "Removing saved recipe")
        }
    }

    // Get all recipes from the Room database
    suspend fun getAllRecipes(): List<RecipeModel> {
        return recipeDao.getAllRecipes().map {
            val jsonObject = JSONObject(it.json)
            jsonObject.apply { put("id", it.internalId) }
            val gson = Gson()
            gson.fromJson(jsonObject.toString(), RecipeDTO::class.java).toModel()
        }
    }

    // Function to insert a saved recipe into the database
    private suspend fun insertSavedRecipe(savedRecipe: SavedRecipeEntity) {
        recipeDao.insertSavedRecipe(savedRecipe)
    }

    // Function to remove a saved recipe from the database
    suspend fun removeSavedRecipe(savedRecipe: SavedRecipeEntity) {
        recipeDao.removeSavedRecipe(savedRecipe)
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
    suspend fun getRecipeById(id: Long): RecipeModel {
        val gson = Gson()
        return gson.fromJson(recipeDao.getRecipeById(id)?.json, RecipeDTO::class.java).toModel()
    }

    // Read recipes from a local JSON file (optional, for initialization or fallback)
    private fun readRecipesFromJson(): List<RecipeDTO> {
        val gson = Gson()
        var recipeList = listOf<RecipeDTO>()
        try {
           val jsonString = """
               [
                   {
                       "recipeID": 1,
                       "name": "Avocado Chicken Salad",
                       "description": "A tasty and healthy avocado chicken salad.",
                       "thumbnailUrl": "https://www.slenderkitchen.com/sites/default/files/styles/gsd-1x1/public/recipe_images/avocado-chicken-salad.jpg",
                       "keywords": "salad, avocado, chicken",
                       "isPublic": true,
                       "userEmail": "Unknown",
                       "originalVideoUrl": "https://s3.amazonaws.com/video-api-prod/assets/a0e1b07dc71c4ac6b378f24493ae2d85/FixedFBFinal.mp4",
                       "country": "US",
                       "numServings": 4,
                       "components": [],
                       "instructions": [
                           {
                               "instructionID": 11,
                               "displayText": "In a large pot, boil water and add pasta. Cook (stirring frequently) until al dente.",
                               "position": 1
                           },
                           {
                               "instructionID": 12,
                               "displayText": "Drain and set pasta aside.",
                               "position": 2
                           },
                           {
                               "instructionID": 13,
                               "displayText": "In the same pan, heat olive oil and 2 tablespoons of butter. Add garlic and crushed red pepper, cook until fragrant.",
                               "position": 3
                           },
                           {
                               "instructionID": 14,
                               "displayText": "Toss in shrimp, salt and pepper to taste, and stir until shrimp start to turn pink, but are not fully cooked.",
                               "position": 4
                           },
                           {
                               "instructionID": 15,
                               "displayText": "Add oregano and spinach, cook until wilted.",
                               "position": 5
                           },
                           {
                               "instructionID": 16,
                               "displayText": "Return cooked pasta to the pot, add remaining butter, parmesan, and parsley. Stir until well mixed and the butter is melted.",
                               "position": 6
                           },
                           {
                               "instructionID": 17,
                               "displayText": "When the shrimp are cooked, add lemon juice, mix once more, then serve while hot.",
                               "position": 7
                           }
                       ]
                   },
                   {
                       "recipeID": 2,
                       "name": "Vegan Lentil Soup",
                       "description": "A hearty vegan lentil soup.",
                       "thumbnailUrl": "https://www.allrecipes.com/thmb/UeFtapHyGFBo4Lx-72GxgjrOGnk=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/13978-lentil-soup-DDMFS-4x3-edfa47fc6b234e6b8add24d44c036d43.jpg",
                       "keywords": "soup, lentil, vegan",
                       "isPublic": true,
                       "userEmail": "Unknown",
                       "originalVideoUrl": "https://www.youtube.com/watch?v=KSyi9U8Zwcc&ab_channel=MarthaStewart",
                       "country": "UK",
                       "numServings": 6,
                       "components": [],
                       "instructions": [
                           {
                               "instructionID": 11,
                               "displayText": "In a large pot, boil water and add pasta. Cook (stirring frequently) until al dente.",
                               "position": 1
                           },
                           {
                               "instructionID": 12,
                               "displayText": "Drain and set pasta aside.",
                               "position": 2
                           },
                           {
                               "instructionID": 13,
                               "displayText": "In the same pan, heat olive oil and 2 tablespoons of butter. Add garlic and crushed red pepper, cook until fragrant.",
                               "position": 3
                           },
                           {
                               "instructionID": 14,
                               "displayText": "Toss in shrimp, salt and pepper to taste, and stir until shrimp start to turn pink, but are not fully cooked.",
                               "position": 4
                           },
                           {
                               "instructionID": 15,
                               "displayText": "Add oregano and spinach, cook until wilted.",
                               "position": 5
                           },
                           {
                               "instructionID": 16,
                               "displayText": "Return cooked pasta to the pot, add remaining butter, parmesan, and parsley. Stir until well mixed and the butter is melted.",
                               "position": 6
                           },
                           {
                               "instructionID": 17,
                               "displayText": "When the shrimp are cooked, add lemon juice, mix once more, then serve while hot.",
                               "position": 7
                           }
                       ]
                   },
                   {
                       "recipeID": 3,
                       "name": "Classic Caesar Salad",
                       "description": "A traditional Caesar salad with croutons and dressing.",
                       "thumbnailUrl": "https://www.allrecipes.com/thmb/mXZ0Tulwn3x9_YB_ZbkiTveDYFE=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/229063-Classic-Restaurant-Caesar-Salad-ddmfs-4x3-231-89bafa5e54dd4a8c933cf2a5f9f12a6f.jpg",
                       "keywords": "salad, caesar, classic",
                       "isPublic": true,
                       "userEmail": "Unknown",
                       "originalVideoUrl": "https://www.google.com/search?q=ceasar+salad&sca_esv=1c412232e298010e&sca_upv=1&biw=1536&bih=695&tbm=vid&sxsrf=ADLYWIJOrXTFUvuqtW0Sx3qBi7VTqUt87g%3A1727071651852&ei=owXxZvnMM9-C9u8P0YnMkAY&ved=0ahUKEwi5qr2is9iIAxVfgf0HHdEEE2IQ4dUDCA0&uact=5&oq=ceasar+salad&gs_lp=Eg1nd3Mtd2l6LXZpZGVvIgxjZWFzYXIgc2FsYWQyCxAAGIAEGJECGIoFMgoQABiABBhDGIoFMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKMgcQABiABBgKSJIDUABYAHAAeACQAQCYAW2gAW2qAQMwLjG4AQPIAQD4AQL4AQGYAgGgAniYAwCSBwMwLjGgB-sH&sclient=gws-wiz-video#fpstate=ive&vld=cid:dc55894c,vid:7Mi8DmbAE74,st:0",
                       "country": "Canada",
                       "numServings": 3,
                       "components": [
                           {
                               "rawText": "1 head romaine lettuce, chopped",
                               "extraComment": "",
                               "ingredient": {
                                   "name": "romaine lettuce"
                               },
                               "measurement": {
                                   "quantity": "1",
                                   "unit": {
                                       "name": "head",
                                       "displaySingular": "head",
                                       "displayPlural": "heads",
                                       "abbreviation": ""
                                   }
                               },
                               "position": 1
                           },
                           {
                               "rawText": "1/2 cup Caesar dressing",
                               "extraComment": "",
                               "ingredient": {
                                   "name": "Caesar dressing"
                               },
                               "measurement": {
                                   "quantity": "0.5",
                                   "unit": {
                                       "name": "cup",
                                       "displaySingular": "cup",
                                       "displayPlural": "cups",
                                       "abbreviation": "c"
                                   }
                               },
                               "position": 2
                           }
                       ],
                       "instructions": [
                           {
                               "instructionID": 11,
                               "displayText": "In a large pot, boil water and add pasta. Cook (stirring frequently) until al dente.",
                               "position": 1
                           },
                           {
                               "instructionID": 12,
                               "displayText": "Drain and set pasta aside.",
                               "position": 2
                           },
                           {
                               "instructionID": 13,
                               "displayText": "In the same pan, heat olive oil and 2 tablespoons of butter. Add garlic and crushed red pepper, cook until fragrant.",
                               "position": 3
                           },
                           {
                               "instructionID": 14,
                               "displayText": "Toss in shrimp, salt and pepper to taste, and stir until shrimp start to turn pink, but are not fully cooked.",
                               "position": 4
                           },
                           {
                               "instructionID": 15,
                               "displayText": "Add oregano and spinach, cook until wilted.",
                               "position": 5
                           },
                           {
                               "instructionID": 16,
                               "displayText": "Return cooked pasta to the pot, add remaining butter, parmesan, and parsley. Stir until well mixed and the butter is melted.",
                               "position": 6
                           },
                           {
                               "instructionID": 17,
                               "displayText": "When the shrimp are cooked, add lemon juice, mix once more, then serve while hot.",
                               "position": 7
                           }
                       ]
                   }
               ]
           """.trimIndent()

//            val jsonObject = JSONObject(jsonString)
//            val recipesArray = jsonObject.getJSONArray("recipes")

            val type = object : TypeToken<List<RecipeDTO>>() {}.type

            recipeList = gson.fromJson(jsonString, type)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return recipeList
    }
}