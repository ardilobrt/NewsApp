package com.and.news.ui.auth.login

interface SignInPresenter {

    fun checkEmpty(email: String, password: String) : Boolean

    fun setSuccess(isSuccess: Boolean)
}