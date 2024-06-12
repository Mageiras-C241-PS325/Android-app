package com.capstone.mageiras.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.ActivityMainBinding
//import com.capstone.mageiras.ui.camera.CameraFragment
import com.capstone.mageiras.ui.home.HomeFragment
import com.capstone.mageiras.ui.recipe.RecipeFragment
import nl.joery.animatedbottombar.AnimatedBottomBar
import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.ui.camerax.CameraXActivity
import com.capstone.mageiras.ui.welcome.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, HomeFragment()).commit()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val newFragment: Fragment = when (item.itemId) {
                R.id.menu_fridge -> HomeFragment()
                R.id.menu_recipes -> RecipeFragment()
                else -> showHomeFragment() // Default ke HomeFragment jika tidak ada yang cocok
            }

            // Melakukan transaksi Fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainLayout, newFragment)
                .commit()

            true // Mengembalikan true untuk menandakan bahwa perubahan item telah ditangani
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, CameraXActivity::class.java)
            startActivity(intent)
        }

    }
    private fun showHomeFragment() : Fragment {
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