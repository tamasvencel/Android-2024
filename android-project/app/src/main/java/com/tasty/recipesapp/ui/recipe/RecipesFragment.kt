package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tasty.recipesapp.R
import com.tasty.recipesapp.RoomDatabase.RecipeDatabase
import com.tasty.recipesapp.adapter.RecipeAdapter
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.model.InstructionModel
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.wrappers.RecipeInstructionsParcelable
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [RecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RecipesFragment : Fragment() {
    // Initialize ViewModel
    private val recipeViewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeRecyclerView: RecyclerView
    private var recipeAdapter: RecipeAdapter? = null
    private lateinit var fabAddRecipe: FloatingActionButton
    private lateinit var recipeDao: RecipeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipes, container, false)

        // Initialize RecyclerView
        recipeRecyclerView = rootView.findViewById(R.id.recyclerView)

        // Set LayoutManager for RecyclerView
        recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fabAddRecipe = rootView.findViewById(R.id.fabAddRecipe)

        fabAddRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_newRecipeFragment)
        }

        recipeViewModel.getAllRecipesFromApi()

        recipeDao = RecipeDatabase.getDatabase(requireContext()).recipeDao()

        recipeViewModel.recipeList.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes != null && recipes.isNotEmpty()) {
                if (recipeAdapter == null) {
                    val recipeAdapter = RecipeAdapter(
                        recipeList = recipes,
                        onItemClick = { recipe -> navigateToRecipeDetail(recipe) },
                        onFavoriteClick = { recipe ->
                            // Toggle favorite status
                            recipe.isFavorite = !recipe.isFavorite

                            recipeViewModel.toggleFavoriteRecipe(recipe)

                            // Find the position of the updated recipe and notify only that item
                            val position = recipes.indexOf(recipe)
                            if (position != -1) {
                                recipeAdapter?.notifyItemChanged(position) // Update only the changed item
                            }
                        },
                        onDeleteClick = null,
                        recipeDao = recipeDao
                    )
                    recipeRecyclerView.adapter = recipeAdapter
                }
                else {
                    // Update the recipe list if the adapter is already initialized
                    recipeAdapter?.recipeList = recipes
                    recipeAdapter?.notifyDataSetChanged() // This will refresh the entire list if needed
                }
            } else {
                Toast.makeText(context, "No recipes available", Toast.LENGTH_SHORT).show()
            }
        })

//        recipeViewModel.getAllRecipesFromApi()

        return rootView
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        val instructionModels = recipe.instructions.mapIndexed { index, instructionText ->
            // Create an InstructionModel for each instruction
            InstructionModel(id = index + 1, displayText = instructionText.displayText, position = index + 1)
        }

        // Now create the RecipeInstructionsParcelable with the List<InstructionModel>
        val instructionsParcelable = RecipeInstructionsParcelable(instructionModels)

        val bundle = Bundle().apply {
            putString("recipeId", recipe.id.toString())
            putString("recipeName", recipe.name)
            putString("recipeDescription", recipe.description)
            putString("recipeThumbnail", recipe.thumbnailUrl)
            putString("recipeOriginalVideoUrl", recipe.originalVideoUrl)
            putParcelable("recipeInstructions", instructionsParcelable) // Use ArrayList as Serializable
        }
        findNavController().navigate(R.id.action_recipesFragment_to_recipeDetailFragment, bundle)
    }
}