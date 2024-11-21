package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.adapter.RecipeAdapter
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
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

        recipeViewModel.fetchRecipeData()

        recipeViewModel.recipeList.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes != null && recipes.isNotEmpty()) {
                val recipeAdapter = RecipeAdapter(
                    recipeList = recipes,
                    onItemClick = { recipe -> navigateToRecipeDetail(recipe) }
                )
                recipeRecyclerView.adapter = recipeAdapter
            } else {
                Toast.makeText(context, "No recipes available", Toast.LENGTH_SHORT).show()
            }
        })

        return rootView
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        findNavController().navigate(
            R.id.action_recipesFragment_to_recipeDetailFragment,
            bundleOf("recipeId" to recipe.id, "recipeName" to recipe.name, "recipeDescription" to recipe.description, "recipeThumbnail" to recipe.thumbnailUrl, "recipeOriginalVideoUrl" to recipe.originalVideoUrl)
        )
    }
}