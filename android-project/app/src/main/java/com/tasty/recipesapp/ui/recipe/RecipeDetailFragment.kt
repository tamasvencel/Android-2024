package com.tasty.recipesapp.ui.recipe

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasty.recipesapp.R
import com.tasty.recipesapp.adapter.InstructionsAdapter
import com.tasty.recipesapp.model.InstructionModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.wrappers.RecipeInstructionsParcelable
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private val recipeViewModel: RecipeListViewModel by viewModels()

    // Declare UI components
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeDescriptionTextView: TextView
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeVideoView: VideoView
    private lateinit var instructionsRecyclerView: RecyclerView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        // Initialize UI components
        recipeNameTextView = rootView.findViewById(R.id.recipeNameTextView)
        recipeDescriptionTextView = rootView.findViewById(R.id.recipeDescriptionTextView)
        recipeImageView = rootView.findViewById(R.id.recipeImageView)
        recipeVideoView = rootView.findViewById(R.id.recipeVideoView)
        instructionsRecyclerView = rootView.findViewById(R.id.instructionsRecyclerView)

        // Retrieve data from the bundle
        val recipeId = arguments?.getInt("recipeId") ?: return rootView
        val recipeName = arguments?.getString("recipeName") ?: ""
        val recipeDescription = arguments?.getString("recipeDescription") ?: ""
        val recipeThumbnail = arguments?.getString("recipeThumbnail") ?: ""
        val recipeOriginalVideoUrl = arguments?.getString("recipeOriginalVideoUrl") ?: ""

        val instructionsParcelable = arguments?.getParcelable<RecipeInstructionsParcelable>("recipeInstructions")
        val recipeInstructions = instructionsParcelable?.instructions ?: listOf()

        // Set data to UI components
        recipeNameTextView.text = recipeName
        recipeDescriptionTextView.text = recipeDescription
        Glide.with(this)
            .load(recipeThumbnail)
            .into(recipeImageView)

        if (recipeOriginalVideoUrl.isNotEmpty()) {
            if (recipeOriginalVideoUrl.endsWith(".mp4")) {
                val videoUri = Uri.parse(recipeOriginalVideoUrl)
                recipeVideoView.visibility = View.VISIBLE
                recipeVideoView.setVideoURI(videoUri)
                recipeVideoView.setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.start() // Autoplay the video when ready

                    // The video height should be fixed at 200dp and width should be match_parent
                    val videoWidth = mediaPlayer.videoWidth
                    val videoHeight = mediaPlayer.videoHeight
                    val aspectRatio = videoWidth.toFloat() / videoHeight.toFloat()

                    // Ensure the video scales correctly within the 200dp height
                    val screenWidth = resources.displayMetrics.widthPixels
                    val adjustedWidth = (630 * aspectRatio).toInt()

                    // Set the fixed height to 200dp and adjust width accordingly
                    val layoutParams = recipeVideoView.layoutParams
                    layoutParams.width = screenWidth // Match parent width
                    layoutParams.height = 580
                    recipeVideoView.layoutParams = layoutParams
                }
            } else {
                // If it's not a direct video link, display the link instead
                recipeVideoView.visibility = View.GONE // Hide the VideoView

                // Create a TextView for the external link
                val externalLinkTextView = TextView(context)
                externalLinkTextView.text = "Watch the video here"
                externalLinkTextView.setTextColor(resources.getColor(R.color.darker_orange, null))
                externalLinkTextView.textSize = 16f
                externalLinkTextView.setPadding(16, 16, 16, 16)
                externalLinkTextView.paintFlags = externalLinkTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

                externalLinkTextView.setOnClickListener {
                    // Open the link in a browser
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(recipeOriginalVideoUrl))
                    startActivity(browserIntent)
                }

                // Add the TextView to the layout
                val layout = rootView.findViewById<LinearLayout>(R.id.recipeDetailLayout)

                val indexOfDescription = layout.indexOfChild(recipeDescriptionTextView)
                layout.addView(externalLinkTextView, indexOfDescription + 1)
            }
        } else {
            recipeVideoView.visibility = View.GONE // Hide the VideoView if no URL
        }

        // Set up the instructions RecyclerView
        instructionsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = InstructionsAdapter(recipeInstructions)
        instructionsRecyclerView.adapter = adapter
        instructionsRecyclerView.visibility = View.VISIBLE

        return rootView
    }
}