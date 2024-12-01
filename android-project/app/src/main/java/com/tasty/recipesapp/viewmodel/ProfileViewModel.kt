package com.tasty.recipesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.mappers.toEntity
import com.tasty.recipesapp.mappers.toSavedRecipeEntity
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeDao: RecipeDao

) : ViewModel() {
    private val _recipes = MutableLiveData<List<RecipeModel>>()
    val recipes: LiveData<List<RecipeModel>> = _recipes

//    suspend fun insertRecipe(recipe: RecipeModel) {
//        recipeRepository.insertRecipe(recipe.toEntity())
//    }

    suspend fun allRecipes(): List<RecipeModel> {
        if (_recipes.value.isNullOrEmpty()) {
            _recipes.value = recipeRepository.getAllRecipes()
        }
        return _recipes.value.orEmpty()
    }

//    suspend fun deleteRecipe(recipe: RecipeModel) {
//        recipeRepository.deleteRecipe(recipe.toEntity())
//    }

    suspend fun getRecipeById(id: Long): RecipeModel {
        return recipeRepository.getRecipeById(id)
    }
}
