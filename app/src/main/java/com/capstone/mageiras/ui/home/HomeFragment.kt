package com.capstone.mageiras.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
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
    private lateinit var recommendedRecipesRV: RecyclerView
    private lateinit var refrigeratorIngredientsRV: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        arguments?.let {
//            listIngredients = it.getString(LIST_INGREDIENTS)
            recipesList = it.getParcelableArrayList<DummyData.Recipes>(LIST_RECIPES)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recommendedRecipesRV = view.findViewById(R.id.carousel_rv_recipes)
        refrigeratorIngredientsRV = view.findViewById(R.id.rv_ingredients)
        showRecyclerList()
        createAction(view)
        showAuth(view)
    }

    private fun createAction(view: View) {
        val settingButton: ImageButton = view.findViewById(R.id.button_settings)
        settingButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
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

    private fun showRecyclerList() {
        recommendedRecipesRV.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val dummyData = DummyData()
        Log.d("List recipes", dummyData.getDummyRecipesData().toString())
        val listRecipesAdapter = ListRecipesAdapter(dummyData.getDummyRecipesData())
        recommendedRecipesRV.adapter = listRecipesAdapter

        refrigeratorIngredientsRV.setLayoutManager(
            LinearLayoutManager(
                requireContext()
            )
        )
        val listIngredientsAdapter = ListIngredientsAdapter(dummyData.getDummyIngredientsData())
        refrigeratorIngredientsRV.adapter = listIngredientsAdapter
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