package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.IngredientDTO
import com.tasty.recipesapp.model.IngredientModel

fun IngredientDTO.toModel(): IngredientModel {
    return IngredientModel(name = this.name)
}