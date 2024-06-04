package com.capstone.mageiras.ui.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.R
import com.capstone.mageiras.databinding.ActivityRegisterBinding
import com.capstone.mageiras.ui.AuthViewModelFactory
import com.capstone.mageiras.ui.main.MainActivity
import com.capstone.mageiras.ui.welcome.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: AuthViewModelFactory = AuthViewModelFactory.getInstance()
        val viewModel: RegisterViewModel = ViewModelProvider(this,factory)[RegisterViewModel::class.java]

        binding.buttonRegister.setOnClickListener {
            viewModel.createAccount(binding.edRegisterEmail.text.toString(),binding.edRegisterPassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}