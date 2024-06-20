package com.capstone.mageiras.ui.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.ActivityRecipeDetailBinding
import com.capstone.mageiras.adapter.RecipeDetailPagerAdapter
import com.capstone.mageiras.data.remote.response.RecipesItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recipes = intent.extras?.getParcelable(EXTRA_RECIPES) as? RecipesItem
        if (recipes != null) {
            setupContent(recipes)
        //TODO CHANGE TO REAL DATA
        setUpTabLayoutWithViewPager(recipes)
        }
        setupAction()
    }

    private fun setupContent(recipes: RecipesItem) {
        binding.tvRecipeName.text = recipes.title
        binding.tvRecipeGenre.text = recipes.genre
        Glide.with(this).load(recipes.imageUrl).into(binding.imvCircularWithStroke)
    }

    private fun setupAction() {
        binding.fabCancel.setOnClickListener {
            finish()
        }
    }

    //TODO CHANGE TO REAL DATA
    private fun setUpTabLayoutWithViewPager(recipes: RecipesItem) {
        val sectionsPagerAdapter = RecipeDetailPagerAdapter(this, recipes)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.recipeDetailTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        for (i in 0 until tabs.tabCount) {
            val textView = LayoutInflater.from(this).inflate(R.layout.tab_title, null)
                    as TextView
            binding.recipeDetailTabs.getTabAt(i)?.customView = textView
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_ingredient,
            R.string.tab_text_steps
        )
        const val EXTRA_RECIPES = "extra_recipes"
    }

}