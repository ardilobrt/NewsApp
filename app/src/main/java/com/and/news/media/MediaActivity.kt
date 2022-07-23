package com.and.news.media

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.and.news.R
import com.and.news.databinding.ActivityMainBinding
import com.and.news.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMediaBinding

    private val MAX_STREAMS = 1

    private lateinit var soundPool: SoundPool

    private var loaded = false

    private var soundId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        binding.btnSound.setOnClickListener{
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build()

            soundPool.setOnLoadCompleteListener { soundPool, i, i2 ->
                loaded = true
            }

            soundId = soundPool.load(this, R.raw.gun,1)

            binding.btnSound.setOnClickListener{
                val serviceSystemManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val actualVolume = serviceSystemManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
                val maxVolume = serviceSystemManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
                val volume = actualVolume / maxVolume

                if(loaded)
                    soundPool.play(soundId, volume, volume, 1,0,1f)
                else
                    Toast.makeText(this, "Soundpool belum diload", Toast.LENGTH_SHORT).show()
            }
        }
    }
}