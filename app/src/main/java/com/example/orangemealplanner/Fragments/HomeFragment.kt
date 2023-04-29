package com.example.orangemealplanner.Fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.orangemealplanner.Listeners.RandomRecipeResponseListener
import com.example.orangemealplanner.Models.RandomRecipeApiResponse
import com.example.orangemealplanner.R
import com.example.orangemealplanner.RequestManager
import com.example.orangemealplanner.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog
    private lateinit var manager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)

        var tags: MutableList<String>
        tags = ArrayList()
        tags.add("breakfast")

        checkIfFragmentAttached {
            manager = RequestManager(requireContext(), tags)
            manager.getRandomRecipes(breakfastRecipeResponseListener)

            tags.clear()
            tags.add("lunch")
            manager = RequestManager(requireContext(), tags)
            manager.getRandomRecipes(lunchRecipeResponseListener)

            tags.clear()
            tags.add("dinner")
            manager = RequestManager(requireContext(), tags)
            manager.getRandomRecipes(dinnerRecipeResponseListener)

        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private val breakfastRecipeResponseListener: RandomRecipeResponseListener =
        object : RandomRecipeResponseListener {
            override fun didFetch(response: RandomRecipeApiResponse, message: String) {
                binding.textViewBreakfastTitle.text = response.recipes[0].title
                Picasso.get().load(response.recipes[0].image).into(binding.imageBreakfast)
                checkIfFragmentAttached {
                    binding.textViewBreakfastLike.text = getString(R.string.like, response.recipes[0].aggregateLikes.toString())
                    binding.textViewBreakfastServings.text = getString(R.string.servings, response.recipes[0].servings.toString())
                    binding.textViewBreakfastTime.text = getString(R.string.minutes, response.recipes[0].readyInMinutes.toString())
                }

            }
            override fun didError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

    private val lunchRecipeResponseListener: RandomRecipeResponseListener =
        object : RandomRecipeResponseListener {
            override fun didFetch(response: RandomRecipeApiResponse, message: String) {
                binding.textViewLunchTitle.text = response.recipes[0].title
                Picasso.get().load(response.recipes[0].image).into(binding.imageLunch)
                checkIfFragmentAttached {
                    binding.textViewLunchLike.text = getString(R.string.like, response.recipes[0].aggregateLikes.toString())
                    binding.textViewLunchServings.text = getString(R.string.servings, response.recipes[0].servings.toString())
                    binding.textViewLunchTime.text = getString(R.string.minutes, response.recipes[0].readyInMinutes.toString())
                }

            }
            override fun didError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

    private val dinnerRecipeResponseListener: RandomRecipeResponseListener =
        object : RandomRecipeResponseListener {
            override fun didFetch(response: RandomRecipeApiResponse, message: String) {
                binding.textViewDinnerTitle.text = response.recipes[0].title
                Picasso.get().load(response.recipes[0].image).into(binding.imageDinner)
                checkIfFragmentAttached {
                    binding.textViewDinnerLike.text = getString(R.string.like, response.recipes[0].aggregateLikes.toString())
                    binding.textViewDinnerServings.text = getString(R.string.servings, response.recipes[0].servings.toString())
                    binding.textViewDinnerTime.text = getString(R.string.minutes, response.recipes[0].readyInMinutes.toString())
                }

                dialog.dismiss()
            }
            override fun didError(message: String) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

    fun checkIfFragmentAttached(operation: Context.() -> Unit) {
        if (isAdded && context != null) {
            operation(requireContext())
        }
    }
}