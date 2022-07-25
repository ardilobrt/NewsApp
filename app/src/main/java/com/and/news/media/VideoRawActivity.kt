package com.and.news.media

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.and.news.R
import com.and.news.databinding.ActivityVideoRawBinding

class VideoRawActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoRawBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoRawBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val path = "android.resource://$packageName/${R.raw.example_video_footage}"


        binding.videoView.setVideoURI(Uri.parse(path))


        binding.btnPlay.setOnClickListener {
            binding.videoView.start()
        }

        binding.btnPause.setOnClickListener {
            binding.videoView.pause()
        }
        binding.btnStop.setOnClickListener {
            binding.videoView.stopPlayback()


            binding.videoView.setVideoURI(Uri.parse(path))
        }


        val controller = MediaController(this)
        controller.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controller)
    }
}