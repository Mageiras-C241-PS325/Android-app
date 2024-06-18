package com.capstone.mageiras.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.data.remote.IDTokenCallback
import com.capstone.mageiras.data.remote.idTokenRetriever
import com.capstone.mageiras.databinding.FragmentHomeBinding
import com.capstone.mageiras.ui.adapter.ListIngredientsAdapter
import com.capstone.mageiras.ui.adapter.ListRecipesAdapter
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
        // Inflate the layout for this fragment
        arguments?.let {
//            listIngredients = it.getString(LIST_INGREDIENTS)
            recipesList = it.getParcelableArrayList<DummyData.Recipes>(LIST_RECIPES)
        }

        // START EXAMPLE USAGE OF idTokenRetriever
        val idTokenCallback = object : IDTokenCallback {
            override fun onCallback(idToken: String?) {
                Log.d("homeidToken", idToken.toString())
            }
        }

        val idToken =  idTokenRetriever(
            idTokenCallback
        )
        // END EXAMPLE USAGE OF idTokenRetriever

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
        binding.buttonSettings.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }
        showAuth(view)
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

    private fun showRecyclerList() {
        binding.carouselRvRecipes.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val dummyData = DummyData()
        Log.d("List recipes", dummyData.getDummyRecipesData().toString())
        val listRecipesAdapter = ListRecipesAdapter(dummyData.getDummyRecipesData())
        binding.carouselRvRecipes.adapter = listRecipesAdapter

        binding.rvIngredients.setLayoutManager(
            LinearLayoutManager(
                requireContext()
            )
        )
        val listIngredientsAdapter = ListIngredientsAdapter(dummyData.getDummyIngredientsData())
        binding.rvIngredients.adapter = listIngredientsAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(ingredientsList: List<DummyData.Ingredients>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    putParcelableArrayList(LIST_INGREDIENTS, ArrayList(ingredientsList))
                    putParcelableArrayList(
                        LIST_RECIPES,
                        ingredientsList as ArrayList<DummyData.Recipes>
                    )
                }
            }
    }
}