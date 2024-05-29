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
import com.capstone.mageiras.ui.camerax.CameraXActivity

class MainActivity : AppCompatActivity() {

    private lateinit var bottomBars: Array<AnimatedBottomBar>
    private lateinit var binding: ActivityMainBinding

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, HomeFragment()).commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val newFragment: Fragment = when (item.itemId) {
                R.id.item1 -> HomeFragment()
                R.id.item3 -> RecipeFragment()
                else -> HomeFragment() // Default ke HomeFragment jika tidak ada yang cocok
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
    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}