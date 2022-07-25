package com.and.news.media

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.and.news.databinding.ActivityVideoFromInternetBinding

class VideoFromInternetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoFromInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoFromInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Play Media From Internet"

        val controller = MediaController(this)
        controller.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controller)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    private fun initializePlayer() {

        binding.loading.visibility = View.VISIBLE
        val videoUri = Uri.parse(URL)
        binding.videoView.setVideoURI(videoUri)

        binding.videoView.setOnPreparedListener {
            binding.loading.visibility = View.GONE
            binding.videoView.seekTo(1)
            binding.videoView.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val URL = "http://techslides.com/demos/sample-videos/small.mp4"
    }
}