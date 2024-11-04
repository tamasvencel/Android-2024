package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.MeasurementDTO
import com.tasty.recipesapp.model.MeasurementModel

fun MeasurementDTO.toModel(): MeasurementModel {
    return MeasurementModel(
        quantity = this.quantity,
        unit = this.unit.toModel()
    )
}