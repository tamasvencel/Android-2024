package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.NutritionDTO
import com.tasty.recipesapp.model.NutritionModel

fun NutritionDTO.toModel(): NutritionModel {
    return NutritionModel(
        calories = this.calories,
        protein = this.protein
    )
}