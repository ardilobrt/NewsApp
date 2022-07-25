package com.and.news.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.and.news.data.remote.model.SignInResponse
import com.and.news.utils.SharedPrefManager
import com.and.news.databinding.ActivitySignInBinding
import com.and.news.ui.auth.register.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setComponent()
        observerValue()
    }

    private fun setComponent() = with(binding) {

        btnSignIn.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            val signInResponse = SignInResponse(email, password)
            viewModel.signInUser(signInResponse, this@SignInActivity)
        }

        btnDontHaveAccount.setOnClickListener {
            Intent(this@SignInActivity, SignUpActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    private fun observerValue() {
        viewModel.dataSuccess.observe(this) { event ->
            event.getContentIfNotHandled().let {
                if (it != null) {
                    showMessage(it)
                    SharedPrefManager.setIsOnLogin(this@SignInActivity, true)
                    finish()
                }
            }
        }

        viewModel.dataError.observe(this) { event ->
            event.getContentIfNotHandled().let {
                if (it != null) {
                    showMessage(it)
                }
            }
        }

        viewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled().let { isLoading ->
                binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
            }
        }

        viewModel.token.observe(this) {
            SharedPrefManager.saveUserToken(this, it)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}