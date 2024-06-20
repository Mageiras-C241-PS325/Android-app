package com.capstone.mageiras.ui.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.databinding.ActivityLoginBinding
import com.capstone.mageiras.ui.AuthViewModelFactory
import com.capstone.mageiras.ui.main.MainActivity
import com.capstone.mageiras.ui.register.RegisterActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import com.capstone.mageiras.data.Result
import okhttp3.RequestBody.Companion.toRequestBody

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val factory: AuthViewModelFactory = AuthViewModelFactory.getInstance()
        val viewModel: LoginViewModel = ViewModelProvider(this,factory)[LoginViewModel::class.java]

        binding.buttonLogin.setOnClickListener {

            if (binding.edLoginEmail.text!!.isEmpty()  || binding.edLoginPassword.text!!.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }


            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            binding.loading.visibility = View.VISIBLE

            viewModel.signIn(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.loading.visibility = View.GONE
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    binding.loading.visibility = View.GONE
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

//            viewModel.login(emailBody, passwordBody).observe(this) {
//                when (it) {
//                    is Result.Loading -> {
//                        Log.d(TAG, "Loading")
//                    }
//
//                    is Result.Success -> {
//                        Log.d(TAG, "Success")
//                        val intent = Intent(this, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                        val token = it.data.idToken
//                    }
//
//                    is Result.Error -> {
//                        Log.d(TAG, "Error")
//                    }
//                }
//            }
        }

        binding.toRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}