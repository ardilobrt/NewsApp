package com.and.news.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.and.news.MainActivity
import com.and.news.data.SharedPrefManager
import com.and.news.data.database.UserDatabase
import com.and.news.databinding.ActivitySignInBinding
import com.and.news.ui.auth.register.SignUpActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity(), SignInView {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var presenter: SignInPresenterImp
    private var database: UserDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        presenter = SignInPresenterImp(this)
        setContentView(binding.root)

        database = UserDatabase.getInstance(this)

        setUiListener()
    }

    private fun setUiListener() = with(binding) {

        btnSignIn.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (presenter.checkEmpty(email, password)) checkUserDatabase(email, password)
        }

        btnDontHaveAccount.setOnClickListener {
            Intent(this@SignInActivity, SignUpActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun checkUserDatabase(email: String, password: String) {

        GlobalScope.launch {
            val result = database?.userDao()?.getUsername(email)

            runOnUiThread {
                if (result != null) {
                    if (presenter.validateUser(result, email, password)) {
                        val user = result.username.toString()
                        goToMain(user)
                    }
                } else showMessage("User Cannot Found")
            }
        }
    }

    private fun goToMain(user: String) {
        Intent(this, MainActivity::class.java).also {
            SharedPrefManager.setIsOnLogin(this@SignInActivity, true)
            SharedPrefManager.saveUserName(this@SignInActivity, user)
            finish()
            startActivity(it)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}