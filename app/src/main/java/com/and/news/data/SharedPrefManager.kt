package com.and.news.data

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {

    private const val SHARE_PREF_FILENAME = "my_shared_pref"
    private const val KEY_IS_ONBOARDING_SHOWN = "user_data_onboarding_shown"
    private const val KEY_IS_ON_LOGIN = "user_on_login"
    private const val KEY_NAME_USER = "user_data_name"

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

    fun saveUserName(context: Context, value: String) {
        val editor = getSharedPref(context).edit()
        editor.putString(KEY_NAME_USER, value)
        editor.apply()
    }

    fun getUserName(context: Context) : String? {
        return getSharedPref(context).getString(KEY_NAME_USER, "")
    }
}