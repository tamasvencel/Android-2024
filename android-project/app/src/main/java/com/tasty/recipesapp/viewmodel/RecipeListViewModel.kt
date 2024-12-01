package com.tasty.recipesapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.entities.SavedRecipeEntity
import com.tasty.recipesapp.mappers.fromSavedRecipeEntity
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
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipeList = MutableLiveData<List<RecipeModel>>()
    val recipeList: LiveData<List<RecipeModel>> = _recipeList

    private val _favoriteRecipes = MutableLiveData<List<RecipeModel>>()
    val favoriteRecipes: LiveData<List<RecipeModel>> = _favoriteRecipes

    fun fetchRecipeData() {
        if (_recipeList.value.isNullOrEmpty()) {
            _recipeList.value = recipeRepository.getRecipes()
        }
    }

    // Load favorite recipes from the repository
    fun loadFavoriteRecipes() {
        viewModelScope.launch {
            val favoriteRecipeEntities = recipeRepository.getSavedRecipes()
            _favoriteRecipes.value = favoriteRecipeEntities
        }
    }

    // Add or remove a recipe from favorites
    fun toggleFavoriteRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            recipeRepository.toggleSavedRecipe(recipe)
            // Reload favorite recipes after toggling
            loadFavoriteRecipes()
        }
    }

    fun deleteRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe) // Toggle bookmark in repository
            loadFavoriteRecipes() // Refresh saved recipes list
        }
    }

    fun insertRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            val newRecipe = recipe.toSavedRecipeEntity()
            Log.d("RecipeListViewModel", "Recipe after converting to entity: ${recipe.toEntity()}")
            recipeRepository.insertRecipe(newRecipe)
            Log.d("RecipeListViewModel", "Recipe inserted: $recipe")
            fetchRecipeData()
        }
    }

    // Check if a recipe is saved in favorites
    fun isRecipeFavorite(recipe: RecipeModel): Boolean {
        return _favoriteRecipes.value?.any { it.id == recipe.id } ?: false
    }
}