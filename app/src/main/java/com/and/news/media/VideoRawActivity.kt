package com.and.news.media

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.and.news.R
import com.and.news.databinding.ActivityVideoRawBinding

class VideoRawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoRawBinding
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoRawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Play Media From Local"

        path = "android.resource://$packageName/${R.raw.example_video_footage}"
        binding.videoView.setVideoURI(Uri.parse(path))

        val controller = MediaController(this)
        controller.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controller)

        setUIListener()
    }

    private fun setUIListener() = with(binding) {
        btnPlay.setOnClickListener {
            binding.videoView.start()
        }

        btnPause.setOnClickListener {
            binding.videoView.pause()
        }

        btnStop.setOnClickListener {
            binding.videoView.stopPlayback()
            binding.videoView.setVideoURI(Uri.parse(path))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}