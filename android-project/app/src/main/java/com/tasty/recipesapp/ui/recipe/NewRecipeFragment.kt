package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasty.recipesapp.R
import com.tasty.recipesapp.entities.RecipeEntity
import com.tasty.recipesapp.model.ComponentModel
import com.tasty.recipesapp.viewmodel.ProfileViewModel
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewRecipeFragment : Fragment() {
    private lateinit var recipeName: EditText
    private lateinit var recipeDescription: EditText
    private lateinit var recipeIngredients: EditText
    private lateinit var recipeInstructions: EditText
    private lateinit var saveRecipeButton: Button

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_new_recipe, container, false)

        recipeName = rootView.findViewById(R.id.recipeName)
        recipeDescription = rootView.findViewById(R.id.recipeDescription)
        recipeIngredients = rootView.findViewById(R.id.recipeIngredients)
        recipeInstructions = rootView.findViewById(R.id.recipeInstructions)
        saveRecipeButton = rootView.findViewById(R.id.saveRecipeButton)

        saveRecipeButton.setOnClickListener {
            val name = recipeName.text.toString()
            val description = recipeDescription.text.toString()
            val ingredients = recipeIngredients.text.toString()
            val instructions = recipeInstructions.text.toString()

            // Check if any required fields are empty and display a message if necessary
            if (name.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a JSON representation of the recipe (optional)
            val jsonRecipe = JSONObject().apply {
                put("name", name)
                put("description", description)
                put("ingredients", ingredients)
                put("instructions", instructions)
            }

            val jsonString = jsonRecipe.toString()

            // Create RecipeEntity with all required fields
            val recipeEntity = RecipeEntity(
                internalId = 0, // Or generate this value if you need it
                json = jsonString
            )

            // Save the recipe to the Room database
            profileViewModel.insertRecipe(recipeEntity)
            Toast.makeText(context, "Recipe saved!", Toast.LENGTH_SHORT).show()

            // Navigate back to the Profile or other screen
            findNavController().popBackStack()
        }

        return rootView
    }
}