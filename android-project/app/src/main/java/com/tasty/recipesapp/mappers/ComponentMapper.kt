package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.ComponentDTO
import com.tasty.recipesapp.model.ComponentModel

fun ComponentDTO.toModel(): ComponentModel {
    return ComponentModel(
        rawText = this.rawText,
        extraComment = this.extraComment,
        ingredient = this.ingredient.toModel(),
        measurement = this.measurement.toModel(),
        position = this.position
    )
}