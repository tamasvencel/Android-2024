package com.tasty.recipesapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val recipeViewModel: RecipeListViewModel by viewModels()
    private lateinit var profileRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        profileRecyclerView = rootView.findViewById(R.id.profileRecyclerView) // replace with actual ID

        // Set the LayoutManager for the RecyclerView
        profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeViewModel.recipeList.observe(viewLifecycleOwner, Observer { recipes ->
            val randomRecipes = recipes.shuffled().take(3) // Pick 3 random recipes
            val profileAdapter = RecipeAdapter(
                recipeList = randomRecipes,
                onItemClick = { recipe -> navigateToRecipeDetail(recipe) }
            )
            profileRecyclerView.adapter = profileAdapter
        })

        recipeViewModel.fetchRecipeData()

        return rootView
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        findNavController().navigate(
            R.id.action_profileFragment_to_recipeDetailFragment,
            bundleOf("recipeId" to recipe.id, "recipeName" to recipe.name, "recipeDescription" to recipe.description, "recipeThumbnail" to recipe.thumbnailUrl)
        )
    }
}