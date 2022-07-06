package com.and.news.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.and.news.databinding.ActivitySignInBinding
import com.and.news.ui.auth.register.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDontHaveAccount.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }
}