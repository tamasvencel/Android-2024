package com.tasty.recipesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.R
import com.tasty.recipesapp.dao.RecipeDao
import com.tasty.recipesapp.mappers.toSavedRecipeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeAdapter(
    var recipeList: List<RecipeModel>,
    private val onItemClick: (RecipeModel) -> Unit,
    private val onFavoriteClick: (RecipeModel) -> Unit,
    private val onDeleteClick: ((RecipeModel) -> Unit)?,
    private val recipeDao: RecipeDao
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val currentUserEmail: String = "user@example.com"

    // ViewHolder to hold the views for each item in the list
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image) // ImageView for the recipe thumbnail
        val recipeDescriptionTextView: TextView = itemView.findViewById(R.id.recipeDescriptionTextView)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon) // Assuming you have a favorite icon
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        init {
            itemView.setOnClickListener {
                onItemClick(recipeList[adapterPosition]) // Trigger the onItemClick lambda when an item is clicked
            }
            // Handle favorite icon click
            favoriteIcon.setOnClickListener {
                val recipe = recipeList[adapterPosition]
                onFavoriteClick(recipeList[adapterPosition]) // Toggle favorite when the icon is clicked
                updateFavoriteIcon(recipe, favoriteIcon)
            }

            // Handle delete button click
            deleteIcon.setOnClickListener {
                val recipe = recipeList[adapterPosition]
                deleteRecipeFromDatabase(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
        holder.recipeDescriptionTextView.text = recipe.description
        // Assuming you have an image URL in your RecipeModel (you can also use a placeholder if necessary)
        Glide.with(holder.itemView.context)
            .load(recipe.thumbnailUrl)  // load image from URL (if available)
            .into(holder.recipeImage)

        // Check if the recipe was created by the current user
        if (recipe.userEmail == currentUserEmail) {
            holder.favoriteIcon.visibility = View.GONE // Hide the favorite button if it was created by the current user
            holder.deleteIcon.visibility = View.VISIBLE
        } else {
            holder.favoriteIcon.visibility = View.VISIBLE // Otherwise, show the favorite button
            holder.deleteIcon.visibility = View.GONE
            // Set the correct icon based on the favorite status
            updateFavoriteIcon(recipe, holder.favoriteIcon)
        }
    }

    private fun updateFavoriteIcon(recipe: RecipeModel, favoriteIcon: ImageView) {
        // Set the favorite icon state (you can set it to a filled heart or outline based on whether it's a favorite)
        if (recipe.isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_filled) // Replace with your filled heart drawable
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border) // Replace with your outline heart drawable
        }
    }

    private fun deleteRecipeFromDatabase(recipe: RecipeModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make sure recipeDao is initialized and delete the recipe
                val savedRecipeEntity = recipe.toSavedRecipeEntity() // Ensure this is correctly mapped
                val savedRecipeInDb = recipeDao.getSavedRecipeByJson(savedRecipeEntity.json)

                if (savedRecipeInDb != null) {
                    recipeDao.removeSavedRecipe(savedRecipeInDb.internalId)
                }

                recipe.isFavorite = !recipe.isFavorite

                // Once deleted, we should remove the recipe from the adapter list as well
                CoroutineScope(Dispatchers.Main).launch {
                    // Remove the recipe from the list
                    val position = recipeList.indexOf(recipe)
                    if (position != -1) {
                        recipeList = recipeList.toMutableList().apply { removeAt(position) }
                        notifyItemRemoved(position) // Notify adapter that the item has been removed
                    }
                }
            } catch (e: Exception) {
                Log.e("RecipeAdapter", "Failed to delete recipe", e)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}