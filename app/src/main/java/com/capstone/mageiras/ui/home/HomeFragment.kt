package com.capstone.mageiras.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mageiras.R
import com.capstone.mageiras.adapter.IngredientAdapter
import com.capstone.mageiras.adapter.ListRecipesAdapter
import com.capstone.mageiras.data.Result
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.FragmentHomeBinding
import com.capstone.mageiras.ui.IngredientViewModelFactory
import com.capstone.mageiras.ui.setting.SettingActivity
import com.capstone.mageiras.ui.welcome.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//private const val LIST_INGREDIENTS = "param1"
private const val LIST_RECIPES = "param2"

class HomeFragment : Fragment() {
    //    private var listIngredients: String? = null
    private var recipesList: ArrayList<DummyData.Recipes>? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            listIngredients = it.getString(LIST_INGREDIENTS)
            recipesList = it.getParcelableArrayList<DummyData.Recipes>(LIST_RECIPES)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSettings.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }
        showAuth(view)

        val factory: IngredientViewModelFactory = IngredientViewModelFactory.getInstance()
        val viewModel: HomeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.getIngredients().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        val data = result.data
                        binding.rvIngredients.setLayoutManager(
                            LinearLayoutManager(
                                requireContext()
                            )
                        )
                        val listIngredientsAdapter = IngredientAdapter(data)
                        binding.rvIngredients.adapter = listIngredientsAdapter
                        if (data.isNullOrEmpty()) {
                            binding.rvIngredients.visibility = View.GONE
                            binding.ivEmptyImage.visibility = View.VISIBLE
                            binding.tvEmptyText.visibility = View.VISIBLE
                        } else {
                            binding.whatInsideRefri.visibility = View.VISIBLE
                            binding.rvIngredients.visibility = View.VISIBLE
                            binding.ivEmptyImage.visibility = View.GONE
                            binding.tvEmptyText.visibility = View.GONE
                        }
                    }

                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        binding.ivEmptyImage.visibility = View.VISIBLE
                        binding.tvEmptyText.visibility = View.VISIBLE
                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        viewModel.getRecipes().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                        binding.tvEmptyText.visibility = View.GONE
                        binding.ivEmptyImage.visibility = View.GONE
                        binding.tvRecommendedRecipes.visibility = View.VISIBLE
                        binding.carouselRvRecipes.visibility = View.VISIBLE

                        val data = result.data.slice(0 until 5)

                        binding.carouselRvRecipes.layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val listIngredientsAdapter = ListRecipesAdapter(data)
                        binding.carouselRvRecipes.adapter = listIngredientsAdapter
                    }

                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        binding.tvRecommendedRecipes.visibility = View.GONE
                        binding.carouselRvRecipes.visibility = View.GONE
                        binding.whatInsideRefri.visibility = View.GONE

                        Toast.makeText(
                            requireActivity(),
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }

    private fun showAuth(view: View) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            Log.d("User", "User is signed in")
            Log.d("user", user.toString())
            Log.d("user", user.displayName.toString())
            Log.d("usertest", user.email.toString())
            val name = user.email
            val tvUsername: TextView = view.findViewById(R.id.tv_profile_username)
            tvUsername.text = name
        } else {
            // No user is signed in
            Log.d("User", "No user is signed in")
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(ingredientsList: List<DummyData.Ingredients>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(
                        LIST_RECIPES,
                        ingredientsList as ArrayList<DummyData.Recipes>
                    )
                }
            }
    }
}