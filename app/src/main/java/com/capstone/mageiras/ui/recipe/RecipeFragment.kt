package com.capstone.mageiras.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.adapter.ListRecipesAdapter
import com.google.android.material.bottomappbar.BottomAppBar

class RecipeFragment : Fragment() {

    private lateinit var recipesRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipesRv = view.findViewById(R.id.rv_recipes)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        recipesRv.layoutManager = LinearLayoutManager(context)
        val dummyData = DummyData()
        val listRecipesAdapter = ListRecipesAdapter(dummyData.getDummyRecipesData())
        recipesRv.adapter = listRecipesAdapter
    }

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