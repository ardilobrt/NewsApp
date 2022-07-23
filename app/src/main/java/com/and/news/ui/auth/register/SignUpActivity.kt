package com.and.news.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.and.news.data.remote.model.SignUpResponse
import com.and.news.databinding.ActivitySignUpBinding
import com.and.news.ui.auth.login.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponent()
        observerValue()
    }

    private fun setupComponent() = with(binding) {
        btnSignUp.setOnClickListener {
            val username = inputUserName.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            val signUpResponse = SignUpResponse(email, password, username)
            viewModel.signUpUser(signUpResponse)
        }

        btnHaveAccount.setOnClickListener {
            goToSignIn()
        }
    }

    private fun observerValue() {
        viewModel.dataSuccess.observe(this) {
            showMessage(it)
            goToSignIn()
        }

        viewModel.dataError.observe(this) {
            showMessage(it)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun goToSignIn() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}