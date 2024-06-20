package com.capstone.mageiras.ui.recipeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.FragmentDetailStepsBinding
import com.capstone.mageiras.adapter.ListStepsAdapter

private const val LIST_STEPS = "listSteps"

class DetailStepsFragment : Fragment() {
    private var listSteps: ArrayList<String>? = null

    private var _binding: FragmentDetailStepsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listSteps = arguments?.getStringArrayList(LIST_STEPS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStepsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListStepsAdapter(listSteps!!)
        binding.rvRecipeDetailIngredients.adapter = adapter
        binding.rvRecipeDetailIngredients.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        binding.tvIngredientsCount.text =
            getString(R.string.steps_amount, listSteps?.size.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(listRecipes: ArrayList<String>) =
            DetailStepsFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(LIST_STEPS, listRecipes)
                }
            }
    }
}