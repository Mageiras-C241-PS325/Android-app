package com.capstone.mageiras.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavType.ParcelableArrayType
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.FragmentHomeBinding
import com.capstone.mageiras.ui.adapter.ListRecipesAdapter

//private const val LIST_INGREDIENTS = "param1"
private const val LIST_RECIPES = "param2"

class HomeFragment : Fragment() {
//    private var listIngredients: String? = null
    private var recipesList: ArrayList<DummyData.Recipes>? = null
    private lateinit var carouselListRecipes: RecyclerView


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
        carouselListRecipes = view.findViewById(R.id.carousel_rv_recipes)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        carouselListRecipes.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        val dummyData = DummyData()
        Log.d("List recipes", dummyData.getDummyRecipesData().toString())
        val listRecipesAdapter = ListRecipesAdapter(dummyData.getDummyRecipesData())
        carouselListRecipes.adapter = listRecipesAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance(ingredientsList: List<DummyData.Ingredients>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    putParcelableArrayList(LIST_INGREDIENTS, ArrayList(ingredientsList))
                    putParcelableArrayList(LIST_RECIPES, ingredientsList as ArrayList<DummyData.Recipes>)                }
            }
    }
}