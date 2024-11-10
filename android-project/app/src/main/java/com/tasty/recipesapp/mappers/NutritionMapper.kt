package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.NutritionDTO
import com.tasty.recipesapp.model.NutritionModel

fun NutritionDTO.toModel(): NutritionModel {
    return NutritionModel(
        calories = this?.calories ?: 0,
        protein = this?.protein ?: 0,
        fat = this?.fat ?: 0,
        carbohydrates = this?.carbohydrates ?: 0,
        sugar = this?.sugar ?: 0,
        fiber = this?.fiber ?: 0
    )
}