package com.and.news.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.and.news.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}