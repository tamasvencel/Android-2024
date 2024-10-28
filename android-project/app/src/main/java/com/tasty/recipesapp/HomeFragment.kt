package com.tasty.recipesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Button

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the Get Started button and set a click listener
        val getStartedButton: Button = view.findViewById(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            // Navigate to the RecipesFragment when the button is clicked
            findNavController().navigate(R.id.recipesFragment)
        }
    }
}