//package com.tasty.recipesapp.ui.recipe
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.tasty.recipesapp.R
//import com.tasty.recipesapp.databinding.FragmentNewRecipeBinding
//import com.tasty.recipesapp.model.ComponentModel
//import com.tasty.recipesapp.model.IngredientModel
//import com.tasty.recipesapp.model.InstructionModel
//import com.tasty.recipesapp.model.MeasurementModel
//import com.tasty.recipesapp.model.NutritionModel
//import com.tasty.recipesapp.model.RecipeModel
//import com.tasty.recipesapp.model.UnitModel
//import com.tasty.recipesapp.viewmodel.ProfileViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class NewRecipeFragment : Fragment() {
//
//    private var _binding: FragmentNewRecipeBinding? = null
//    private val binding get() = _binding!!
//
//    private val profileViewModel: ProfileViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
//        setupListeners()
//        return binding.root
//    }
//
//    private fun setupListeners() {
//        binding.addIngredientButton.setOnClickListener {
//            addDynamicField(
//                binding.ingredientsLayout,
//                "Ingredient"
//            )
//        }
//        binding.addInstructionButton.setOnClickListener {
//            addDynamicField(
//                binding.instructionsLayout,
//                "Instruction"
//            )
//        }
//        binding.saveRecipeButton.setOnClickListener { saveRecipe() }
//    }
//
//    private fun addDynamicField(layout: LinearLayout, hint: String) {
//        val editText = EditText(requireContext()).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            this.hint = hint
//            textSize = 16f
//            setPadding(16, 16, 16, 16)
//        }
//        layout.addView(editText)
//    }
//
//    private fun saveRecipe() {
//        val title = binding.recipeTitle.text.toString()
//        val description = binding.recipeDescription.text.toString()
//        val pictureUrl = binding.recipePictureUrl.text.toString()
//
//        // Collect Nutrition Details
//        val nutrition = NutritionModel(
//            calories = binding.nutritionCalories.text.toString().toIntOrNull() ?: 0,
//            protein = binding.nutritionProtein.text.toString().toIntOrNull() ?: 0,
//            fat = binding.nutritionFat.text.toString().toIntOrNull() ?: 0,
//            carbs = 0,
//            sugar = 0,
//            fiber = 0
//        )
//
//        // Collect Ingredients
//        val ingredients = getDynamicFields(binding.ingredientsLayout)
//
//        // Collect Instructions
//        val instructions = getDynamicFields(binding.instructionsLayout)
//
//        // Build Recipe Model
//        val recipe = RecipeModel(
//            recipeID = 0,
//            name = title,
//            description = description,
//            thumbnailUrl = pictureUrl,
//            keywords = emptyList(), // Replace with actual keywords if needed
//            isPublic = true,
//            userEmail = "user@example.com", // Replace with actual email
//            originalVideoUrl = "", // Optional
//            country = "US", // Default or user-provided
//            servings = 1, // Default or user-provided
//            components = ingredients.mapIndexed { index, ingredient ->
//                ComponentModel(
//                    rawText = ingredient,
//                    extraComment = "",
//                    ingredient = IngredientModel(name = ingredient),
//                    measurement = MeasurementModel(
//                        quantity = "1",
//                        unit = UnitModel(
//                            name = "unit",
//                            displaySingular = "unit",
//                            displayPlural = "units",
//                            abbreviation = "u"
//                        )
//                    ),
//                    position = index + 1
//                )
//            },
//            instructions = instructions.mapIndexed { index, instruction ->
//                InstructionModel(
//                    id = index + 1,
//                    displayText = instruction,
//                    step = index + 1
//                )
//            },
//            nutrition = nutrition
//        )
//
//        // Insert recipe into the database using ProfileViewModel
//        viewLifecycleOwner.lifecycleScope.launch {
//            profileViewModel.insertRecipe(recipe)
//        }
//
//        Toast.makeText(requireContext(), "Recipe saved!", Toast.LENGTH_SHORT).show()
//
//        // Clear all fields
//        binding.recipeTitle.text?.clear()
//        binding.recipeDescription.text?.clear()
//        binding.recipePictureUrl.text?.clear()
//        binding.nutritionCalories.text?.clear()
//        binding.nutritionProtein.text?.clear()
//        binding.nutritionFat.text?.clear()
//        binding.ingredientsLayout.removeAllViews()
//        binding.instructionsLayout.removeAllViews()
//
//        findNavController().navigate(R.id.profileFragment)
//
//    }
//
//    private fun getDynamicFields(layout: LinearLayout): List<String> {
//        val fields = mutableListOf<String>()
//        for (i in 0 until layout.childCount) {
//            val view = layout.getChildAt(i)
//            if (view is EditText) {
//                fields.add(view.text.toString())
//            }
//        }
//        return fields
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
