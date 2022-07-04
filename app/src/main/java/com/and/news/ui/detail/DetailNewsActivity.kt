package com.and.news.ui.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.and.news.R
import com.and.news.data.MyCompanion
import com.and.news.data.MyCompanion.loadImageDetail
import com.and.news.data.model.ArticlesItem
import com.and.news.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding
    private val viewModel by viewModels<DetailNewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val articles =
            intent.getParcelableExtra<ArticlesItem>(MyCompanion.EXTRA_ARTICLES) as ArticlesItem

        viewModel.getDetails(articles)

        viewModel.item.observe(this) {
            if (it != null) {
                binding.apply {
                    ivUrlImage.loadImageDetail(it.urlToImage)
                    chipDetailSource.text = it.source?.name
                    tvDetailTitle.text = it.title
                    includeContent.tvContent.text = it.description ?: getString(R.string.empty_content)
                    includeContent.tvLink.setOnClickListener { _ ->
                        Intent(Intent.ACTION_VIEW).also { intent ->
                            intent.data = Uri.parse(it.url)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}