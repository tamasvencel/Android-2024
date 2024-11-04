package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.UnitDTO
import com.tasty.recipesapp.model.UnitModel

fun UnitDTO.toModel(): UnitModel {
    return UnitModel(
        name = this.name,
        displaySingular = this.displaySingular,
        displayPlural = this.displayPlural,
        abbreviation = this.abbreviation
    )
}