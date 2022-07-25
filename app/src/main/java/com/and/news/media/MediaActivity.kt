package com.and.news.media

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.and.news.R
import com.and.news.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var soundPool: SoundPool
    private var loaded = false
    private var soundId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Play Media"

        setAudioAttribute()
        setUIListener()
    }

    private fun setAudioAttribute() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(MAX_STREAMS)
            .build()

        soundPool.setOnLoadCompleteListener { _, _, _ ->
            loaded = true
        }
        soundId = soundPool.load(this, R.raw.gun, 1)
    }

    private fun setUIListener() = with(binding) {
        btnSound.setOnClickListener {
            val serviceSystemManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val actualVolume =
                serviceSystemManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
            val maxVolume =
                serviceSystemManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
            val volume = actualVolume / maxVolume

            if (loaded)
                soundPool.play(soundId, volume, volume, 1, 0, 1f)
            else Toast.makeText(this@MediaActivity, "Soundpool belum diload", Toast.LENGTH_SHORT)
                .show()
        }

        btnPlayRaw.setOnClickListener {
            val intent = Intent(this@MediaActivity, VideoRawActivity::class.java)
            startActivity(intent)
        }

        btnPlayInternet.setOnClickListener {
            val intent = Intent(this@MediaActivity, VideoFromInternetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val MAX_STREAMS = 1
    }
}