package com.capstone.mageiras.ui.recipeDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.FragmentDetailIngredientsBinding
import com.capstone.mageiras.adapter.ListIngredientsAdapter
import com.capstone.mageiras.data.remote.response.IngredientsItem
import com.capstone.mageiras.data.remote.response.RecipesItem

private const val LIST_INGREDIENTS = "listIngredients"

class DetailIngredientsFragment : Fragment() {
    private var listIngredients: ArrayList<IngredientsItem>? = null

    private var _binding: FragmentDetailIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listIngredients = arguments?.getParcelableArrayList<IngredientsItem>(LIST_INGREDIENTS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DetailIngredientsFragment", "onViewCreated: $listIngredients")
        val adapter = ListIngredientsAdapter(listIngredients!!)
        binding.rvRecipeDetailIngredients.adapter = adapter
        binding.rvRecipeDetailIngredients.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        binding.tvIngredientsCount.text =
            getString(R.string.ingredients_amount, listIngredients?.size.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(listIngredients: ArrayList<IngredientsItem>) =
            DetailIngredientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_INGREDIENTS, listIngredients)
                }
            }
    }
}