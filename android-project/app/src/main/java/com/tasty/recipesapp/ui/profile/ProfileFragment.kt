package com.tasty.recipesapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.adapter.RecipeAdapter
import com.tasty.recipesapp.model.InstructionModel
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.viewmodel.ProfileViewModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.wrappers.RecipeInstructionsParcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val recipeViewModel: RecipeListViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var profileRecyclerView: RecyclerView
    private var profileAdapter: RecipeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        profileRecyclerView = rootView.findViewById(R.id.profileRecyclerView) // replace with actual ID

        // Set the LayoutManager for the RecyclerView
        profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        recipeViewModel.favoriteRecipes.observe(viewLifecycleOwner, Observer { favoriteRecipes ->
            if (profileAdapter == null) {
                 profileAdapter = RecipeAdapter(
                    recipeList = favoriteRecipes,
                    onItemClick = { recipe -> navigateToRecipeDetail(recipe) },
                    onFavoriteClick = { recipe ->
                        recipe.isFavorite = !recipe.isFavorite
                        recipeViewModel.toggleFavoriteRecipe(recipe) // Toggle favorite when clicked
                    }
                )
                profileRecyclerView.adapter = profileAdapter
            } else {
                profileAdapter?.recipeList = favoriteRecipes
                profileAdapter?.notifyDataSetChanged()
            }
        })

        recipeViewModel.loadFavoriteRecipes()

        return rootView
    }


    private fun fetchAndSetRecipes() {
        // Use lifecycleScope to launch the coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.allRecipes()
        }
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

        findNavController().navigate(
            R.id.action_profileFragment_to_recipeDetailFragment,
            bundle
        )
    }
}