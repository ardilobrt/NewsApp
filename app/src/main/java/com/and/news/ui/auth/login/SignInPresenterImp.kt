package com.and.news.ui.auth.login

import com.and.news.ui.auth.register.SignUpPresenter

class SignInPresenterImp(val view: SignInView) : SignUpPresenter {

    override fun checkEmpty(username: String, email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSuccess(isSuccess: Boolean) {
        TODO("Not yet implemented")
    }
}