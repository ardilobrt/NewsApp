package com.and.news.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.and.news.databinding.ActivitySignUpBinding
import com.and.news.ui.auth.login.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            btnSignUp.setOnClickListener {
                val username = inputUserName.text.toString()
                val email = inputEmail.text.toString()
                val password = inputPassword.text.toString()


            }

            btnHaveAccount.setOnClickListener {
                goToSignIn()
            }
        }
    }

    private fun goToSignIn() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}