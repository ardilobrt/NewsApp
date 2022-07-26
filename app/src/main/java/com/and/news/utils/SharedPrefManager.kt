package com.and.news.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {

    private const val SHARE_PREF_FILENAME = "my_shared_pref"
    private const val KEY_IS_ONBOARDING_SHOWN = "user_data_onboarding_shown"
    private const val KEY_IS_ON_LOGIN = "user_on_login"
    private const val KEY_TOKEN_USER = "user_token"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_PASSWORD = "user_password"

    private fun getSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARE_PREF_FILENAME, 0)
    }

    fun setIsOnBoardingShown(context: Context, isShown: Boolean) {
        val editor = getSharedPref(context).edit()
        editor.putBoolean(KEY_IS_ONBOARDING_SHOWN, isShown)
        editor.apply()
    }

    fun getIsOnBoardingShown(context: Context): Boolean {
        return getSharedPref(context).getBoolean(KEY_IS_ONBOARDING_SHOWN, false)
    }

    fun setIsOnLogin(context: Context, isShown: Boolean) {
        val editor = getSharedPref(context).edit()
        editor.putBoolean(KEY_IS_ON_LOGIN, isShown)
        editor.apply()
    }

    fun getIsOnLogin(context: Context): Boolean {
        return getSharedPref(context).getBoolean(KEY_IS_ON_LOGIN, false)
    }

    fun saveUserToken(context: Context, value: String) {
        val editor = getSharedPref(context).edit()
        editor.putString(KEY_TOKEN_USER, value)
        editor.apply()
    }

    fun getUserToken(context: Context) : String? {
        return getSharedPref(context).getString(KEY_TOKEN_USER, "")
    }

    fun saveEmail(context: Context, value: String) {
        val editor = getSharedPref(context).edit()
        editor.putString(KEY_EMAIL, value)
        editor.apply()
    }

    fun getEmail(context: Context) : String? {
        return getSharedPref(context).getString(KEY_EMAIL, "")
    }

    fun savePassword(context: Context, value: String) {
        val editor = getSharedPref(context).edit()
        editor.putString(KEY_PASSWORD, value)
        editor.apply()
    }

    fun getPassword(context: Context) : String? {
        return getSharedPref(context).getString(KEY_PASSWORD, "")
    }
}