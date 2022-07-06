package com.and.news.ui.auth.login

import com.and.news.data.entity.Users
import com.and.news.ui.auth.register.SignUpPresenter

class SignInPresenterImp(val view: SignInView) : SignInPresenter {

    override fun checkEmpty(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            view.showMessage("Please Fill All Field")
        } else return true

        return false
    }

    override fun validateUser(users: Users, email: String, password: String): Boolean {
        return users.email.toString() == email && users.password.toString() == password
    }

    override fun setSuccess(isSuccess: Boolean) {
        if (isSuccess) view.showMessage("Login Success")
        else view.showMessage("Username & Password Not Match")
    }
}