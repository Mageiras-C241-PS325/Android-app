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
import com.capstone.mageiras.ui.login.LoginActivity
import com.capstone.mageiras.ui.main.MainActivity
import com.capstone.mageiras.ui.welcome.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.capstone.mageiras.data.Result
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

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

        // Check for confirm password is the same as password
        binding.edRegisterConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.edRegisterPassword.text.toString() != binding.edRegisterConfirmPassword.text.toString()) {
                    binding.tilConfirmPassword.error = "Password not match"
                    binding.edRegisterConfirmPassword.error = "Password not match"
                } else {
                    binding.tilConfirmPassword.error = null
                    binding.edRegisterConfirmPassword.error = null
                }
            }
        }

        binding.toLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
//            viewModel.createAccount(binding.edRegisterEmail.text.toString(),binding.edRegisterPassword.text.toString()).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val intent = Intent(this, WelcomeActivity::class.java)
//                    intent.flags =
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                } else {
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
            if (binding.edRegisterUsername.text.toString().isEmpty()) {
                binding.edRegisterUsername.error = "Username is required"
                return@setOnClickListener
            }
            if (binding.edRegisterEmail.text.toString().isEmpty()) {
                binding.edRegisterEmail.error = "Email is required"
                return@setOnClickListener
            }
            if (binding.edRegisterPassword.text.toString().isEmpty()) {
                binding.edRegisterPassword.error = "Password is required"
                return@setOnClickListener
            }

            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val username = binding.edRegisterUsername.text.toString()

            val emailBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
            val passwordBody = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
            val usernameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), username)
            viewModel.register(emailBody,passwordBody,usernameBody).observe(this) {
                when(it){
                    is Result.Success -> {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    is Result.Loading -> {
                        Log.d(TAG, "Loading")
                    }
                }
            }
        }
    }
}