package com.tasty.recipesapp.mappers

import com.tasty.recipesapp.dto.InstructionDTO
import com.tasty.recipesapp.model.InstructionModel

fun InstructionDTO.toModel(): InstructionModel {
    return InstructionModel(
        id = this.instructionID,
        displayText = this.displayText,
        position = this.position
    )
}