package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.FragmentNewRecipeBinding
import com.tasty.recipesapp.model.InstructionModel
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewRecipeFragment : Fragment() {

    private var _binding: FragmentNewRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.addInstructionButton.setOnClickListener {
            addDynamicField(
                binding.instructionsLayout,
                "Instruction"
            )
        }
        binding.saveRecipeButton.setOnClickListener { saveRecipe() }
    }

    private fun addDynamicField(layout: LinearLayout, hint: String) {
        val editText = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            this.hint = hint
            textSize = 16f
            setPadding(16, 16, 16, 16)

            // Set hint text color
            setHintTextColor(ContextCompat.getColor(context, R.color.dark_gray))

            // Set text color
            setTextColor(ContextCompat.getColor(context, R.color.better_white))

            // Set background tint
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
        }
        layout.addView(editText)
    }

    private fun saveRecipe() {
        val title = binding.recipeTitle.text.toString()
        val description = binding.recipeDescription.text.toString()
        val pictureUrl = binding.recipePictureUrl.text.toString()

        // Collect Instructions
        val instructions = getDynamicFields(binding.instructionsLayout)

        // Build Recipe Model
        val recipe = RecipeModel(
            id = 0,
            name = title,
            description = description,
            thumbnailUrl = pictureUrl,
            instructions = instructions.mapIndexed { index, instruction ->
                InstructionModel(
                    id = index + 1,
                    displayText = instruction,
                    position = index + 1
                )
            },
            isPublic = true,
            userEmail = "user@example.com",  // Use the actual user email
            originalVideoUrl = "",
            country = "US",
            numServings = 1,
            components = emptyList(),
            isFavorite = true
        )

        // Insert recipe into the database using RecipeListViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d("NewRecipeFragment", "Before recipe insertion: $recipe")
                recipeViewModel.insertRecipe(recipe)
                Log.d("NewRecipeFragment", "Recipe inserted successfully.")

                // Toast confirmation
                Toast.makeText(requireContext(), "Recipe saved!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("NewRecipeFragment", "Error inserting recipe: ${e.message}")
            }
        }

        // Clear all fields
        binding.recipeTitle.text?.clear()
        binding.recipeDescription.text?.clear()
        binding.recipePictureUrl.text?.clear()
        binding.instructionsLayout.removeAllViews()

        findNavController().navigate(R.id.action_newRecipeFragment_to_recipesFragment)
    }

    private fun getDynamicFields(layout: LinearLayout): List<String> {
        val fields = mutableListOf<String>()
        for (i in 0 until layout.childCount) {
            val view = layout.getChildAt(i)
            if (view is EditText) {
                fields.add(view.text.toString())
            }
        }
        return fields
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
