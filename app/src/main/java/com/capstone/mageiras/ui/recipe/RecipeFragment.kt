package com.capstone.mageiras.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mageiras.adapter.ListRecipesAdapter
import com.capstone.mageiras.data.Result
import com.capstone.mageiras.databinding.FragmentRecipeBinding
import com.capstone.mageiras.ui.IngredientViewModelFactory

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showRecyclerList()

        val factory: IngredientViewModelFactory = IngredientViewModelFactory.getInstance()
        val viewModel: RecipeViewModel =
            ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        viewModel.getRecipes().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                        binding.ivNoRecipe.visibility = View.GONE
                        binding.tvNoRecipe.visibility = View.GONE
                        binding.rvRecipes.visibility = View.VISIBLE

                        val data = result.data

                        binding.rvRecipes.layoutManager = LinearLayoutManager(requireActivity())
                        val listIngredientsAdapter = ListRecipesAdapter(data)
                        binding.rvRecipes.adapter = listIngredientsAdapter


//                        binding.rvIngredients.setLayoutManager(
//                            LinearLayoutManager(
//                                requireContext()
//                            )
//                        )
//                        val listIngredientsAdapter = RecipeAdapter(data)
//                        binding.rvIngredients.adapter = listIngredientsAdapter
                    }

                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                        binding.ivNoRecipe.visibility = View.VISIBLE
                        binding.tvNoRecipe.visibility = View.VISIBLE
                        binding.rvRecipes.visibility = View.GONE
                    }
                }
            }
        }

    }

//    private fun showRecyclerList() {
//        recipesRv.layoutManager = LinearLayoutManager(context)
//        val dummyData = DummyData()
//        val listRecipesAdapter = ListRecipesAdapter(dummyData.getDummyRecipesData())
//        recipesRv.adapter = listRecipesAdapter
//    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}