package com.and.news.ui.auth.register

class SignUpPresenterImp(val view: SignUpView) : SignUpPresenter {

    override fun checkEmpty(username: String, email: String, password: String): Boolean {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            view.showMessage("Please Fill All Field")
        } else return true

        return false
    }

    override fun setSuccess(isSuccess: Boolean) {
        if (isSuccess) view.showMessage("Register Success") else view.showMessage("Register Failed")
        view.clearField()
    }
}