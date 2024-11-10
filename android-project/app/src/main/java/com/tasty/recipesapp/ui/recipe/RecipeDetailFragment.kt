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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        val recipeId = arguments?.getInt("recipeId") ?: return rootView

        recipeViewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            val selectedRecipe = recipes.find { it.id == recipeId }
            selectedRecipe?.let {
                // Set the recipe name in a TextView
                val recipeNameTextView: TextView = rootView.findViewById(R.id.recipeNameTextView)
                recipeNameTextView.text = it.name

                // Set the recipe description in a TextView
                val recipeDescriptionTextView: TextView = rootView.findViewById(R.id.recipeDescriptionTextView)
                recipeDescriptionTextView.text = it.description

                // Set the number of servings in a TextView
                val numServingsTextView: TextView = rootView.findViewById(R.id.numServingsTextView)
                numServingsTextView.text = "Servings: ${it.numServings}"

                // Set the recipe's thumbnail image in an ImageView (optional)
                val thumbnailImageView: ImageView = rootView.findViewById(R.id.recipeThumbnailImageView)
                Glide.with(requireContext())
                    .load(it.thumbnailUrl) // Using Glide to load the image into the ImageView
                    .into(thumbnailImageView)

                // If you have a video URL for the recipe, you can also set it here (optional)
                val videoUrlTextView: TextView = rootView.findViewById(R.id.videoUrlTextView)
                videoUrlTextView.text = "Video: ${it.originalVideoUrl}"

                // If you want to show the keywords, you can also display them in a TextView
                val keywordsTextView: TextView = rootView.findViewById(R.id.keywordsTextView)
                keywordsTextView.text = "Keywords: ${it.keywords}"
            }
            // Update UI with the selected recipe's details
        }

        return rootView
    }
}