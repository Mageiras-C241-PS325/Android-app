package com.capstone.mageiras.ui.main

//import com.capstone.mageiras.ui.camera.CameraFragment
import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.ActivityMainBinding
import com.capstone.mageiras.ui.camerax.CameraXActivity
import com.capstone.mageiras.ui.home.HomeFragment
import com.capstone.mageiras.ui.recipe.RecipeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, HomeFragment()).commit()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val newFragment: Fragment? = when (item.itemId) {
                R.id.menu_fridge -> HomeFragment()
                R.id.menu_recipes -> RecipeFragment()
                R.id.placeholder -> null
                else -> showHomeFragment() // Default ke HomeFragment jika tidak ada yang cocok
            }

            // Melakukan transaksi Fragment
            if (newFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainLayout, newFragment)
                    .commit()
            } else {
                false
            }

            true // Mengembalikan true untuk menandakan bahwa perubahan item telah ditangani
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CameraXActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showHomeFragment(): Fragment {
        val homeFragment = HomeFragment()
        val bundle = Bundle()
        val dummyData = DummyData()
        val listRecipes = dummyData.getDummyRecipesData()
        bundle.putParcelableArrayList("LIST_RECIPES", ArrayList(listRecipes))
        homeFragment.arguments = bundle
        return homeFragment
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}