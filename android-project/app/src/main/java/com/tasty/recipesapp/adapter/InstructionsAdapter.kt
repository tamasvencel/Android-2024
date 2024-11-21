package com.tasty.recipesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.model.InstructionModel

class InstructionsAdapter(private var instructions: List<InstructionModel>) :
    RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder>() {
    init {
        // Sort the instructions by position when the adapter is created
        instructions = instructions.sortedBy { it.position }
    }

    inner class InstructionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare the views for instruction position and text
        val instructionStep: TextView = itemView.findViewById(R.id.instructionPosition)
        val instructionText: TextView = itemView.findViewById(R.id.instructionText)

        @SuppressLint("SetTextI18n")
        fun bind(instruction: InstructionModel) {
            // Set the text for the step and instruction
            instructionStep.text = "Step ${instruction.position}"
            instructionText.text = instruction.displayText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        // Inflate the view for the instruction item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return InstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        // Bind the instruction data to the views
        holder.bind(instructions[position])
    }

    override fun getItemCount(): Int {
        return instructions.size
    }
}