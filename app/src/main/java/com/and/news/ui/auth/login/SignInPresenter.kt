package com.and.news.ui.auth.login

import com.and.news.data.local.entity.Users

interface SignInPresenter {

    fun checkEmpty(email: String, password: String): Boolean

    fun validateUser(users: Users, email: String, password: String): Boolean

    fun setSuccess(isSuccess: Boolean)
}