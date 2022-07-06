package com.and.news.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.and.news.databinding.ActivitySignUpBinding
import com.and.news.ui.auth.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHaveAccount.setOnClickListener {
            Intent(this, SignInActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }
}