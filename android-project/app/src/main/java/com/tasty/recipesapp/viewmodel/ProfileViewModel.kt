package com.tasty.recipesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.repository.RecipeRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val allRecipes = liveData {
        emit(recipeRepository.getAllRecipes())
    }

    fun insertRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }
}