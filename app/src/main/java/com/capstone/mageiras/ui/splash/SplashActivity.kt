package com.capstone.mageiras.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.mageiras.R
import com.capstone.mageiras.ui.main.MainActivity
import com.capstone.mageiras.ui.onboarding.OnBoardingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }, SPLASH_TIME_OUT)

    }
    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}