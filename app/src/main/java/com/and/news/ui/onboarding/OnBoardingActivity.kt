package com.and.news.ui.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsetsController
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.and.news.ui.MainActivity
import com.and.news.adapter.ViewPagerAdapter
import com.and.news.utils.SharedPrefManager
import com.and.news.databinding.ActivityOnBoardingBinding
import java.util.*
import kotlin.concurrent.schedule

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private var contentHasLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timer().schedule(2000) {
            contentHasLoaded = true
        }
        setupSplashScreen()

        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
        setStatusBar(false)

        val isOnBoardingShow = SharedPrefManager.getIsOnBoardingShown(this)
        if (isOnBoardingShow) goToMain()

        binding.apply {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            dotsIndicator.attachTo(viewPager)
        }

        binding.btnNext.setOnClickListener {
            setStatusBar(true)
            SharedPrefManager.setIsOnBoardingShown(this, true)
            goToMain()
        }
    }

    // Setup Splash Screen
    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }

    // Setup Action Bar
    private fun setStatusBar(isFlagCleared: Boolean) {
        if (!isFlagCleared) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    } else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } else window.insetsController?.setSystemBarsAppearance(
            0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }

    private fun goToMain() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}