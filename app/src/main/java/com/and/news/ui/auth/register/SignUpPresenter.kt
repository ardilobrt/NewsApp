package com.and.news.ui.auth.register

interface SignUpPresenter {

    fun checkEmpty(username: String, email: String, password: String) : Boolean

    fun setSuccess(isSuccess: Boolean)
}