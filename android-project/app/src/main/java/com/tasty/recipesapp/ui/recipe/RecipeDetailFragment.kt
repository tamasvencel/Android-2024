package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tasty.recipesapp.R
import com.tasty.recipesapp.viewmodel.RecipeListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailFragment : Fragment() {

    private val recipeViewModel: RecipeListViewModel by viewModels()

    // Declare UI components
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeDescriptionTextView: TextView
    private lateinit var recipeImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        // Initialize UI components
        recipeNameTextView = rootView.findViewById(R.id.recipeNameTextView)
        recipeDescriptionTextView = rootView.findViewById(R.id.recipeDescriptionTextView)
        recipeImageView = rootView.findViewById(R.id.recipeImageView)

        // Retrieve data from the bundle
        val recipeId = arguments?.getInt("recipeId") ?: return rootView
        val recipeName = arguments?.getString("recipeName") ?: ""
        val recipeDescription = arguments?.getString("recipeDescription") ?: ""
        val recipeThumbnail = arguments?.getString("recipeThumbnail") ?: ""

        // Set data to UI components
        recipeNameTextView.text = recipeName
        recipeDescriptionTextView.text = recipeDescription
        Glide.with(this)
            .load(recipeThumbnail)
            .into(recipeImageView)

        recipeViewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            val selectedRecipe = recipes.find { it.id == recipeId }
            selectedRecipe?.let {
                // Set the recipe name in a TextView
                val recipeNameTextView: TextView = rootView.findViewById(R.id.recipeNameTextView)
                recipeNameTextView.text = it.name

                // Set the recipe description in a TextView
                val recipeDescriptionTextView: TextView = rootView.findViewById(R.id.recipeDescriptionTextView)
                recipeDescriptionTextView.text = it.description
            }
            // Update UI with the selected recipe's details
        }

        return rootView
    }
}