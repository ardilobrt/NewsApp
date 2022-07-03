package com.and.news.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.and.news.R
import com.and.news.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySignInBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySignInBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}