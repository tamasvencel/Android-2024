package com.tasty.recipesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasty.recipesapp.model.RecipeModel
import com.tasty.recipesapp.R

class RecipeAdapter(
    private val recipeList: List<RecipeModel>,
    private val onItemClick: (RecipeModel) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    // ViewHolder to hold the views for each item in the list
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image) // ImageView for the recipe thumbnail
        val recipeDescriptionTextView: TextView = itemView.findViewById(R.id.recipeDescriptionTextView)

        init {
            itemView.setOnClickListener {
                onItemClick(recipeList[adapterPosition]) // Trigger the onItemClick lambda when an item is clicked
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
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}