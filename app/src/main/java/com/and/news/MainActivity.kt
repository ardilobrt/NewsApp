package com.and.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.and.news.data.SharedPrefManager
import com.and.news.databinding.ActivityMainBinding
import com.and.news.ui.bookmark.BookmarkFragment
import com.and.news.ui.home.HomeFragment
import com.and.news.ui.profile.AuthorizedFragment
import com.and.news.ui.profile.ProfileFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDashboard()
    }

    private fun showDashboard() {

        replaceFragment(HomeFragment())

        val username = SharedPrefManager.getUserName(this).toString()

        if (username.isEmpty()) {
            showUser("READER")
            SharedPrefManager.setIsOnLogin(this, false)
        } else showUser(username)

        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_bookmark -> replaceFragment(BookmarkFragment())
                R.id.navigation_profile -> {
                    val isOnLogin = SharedPrefManager.getIsOnLogin(this)
                    if (isOnLogin) replaceFragment(ProfileFragment())
                    else replaceFragment(AuthorizedFragment())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_view, fragment)
        }
    }

    private fun showUser(username: String) {
        val snackBar =
            Snackbar.make(binding.root, "HELLO ${username.uppercase()}", Snackbar.LENGTH_LONG)
        snackBar.setAction("CLOSE") {
            snackBar.dismiss()
        }
        snackBar.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.purple_lighter))
        snackBar.show()
    }

    override fun onPause() {
        super.onPause()
        Log.d(MainActivity::class.java.simpleName, "onPause: YES")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MainActivity::class.java.simpleName, "onResume: YES")
    }
}