package com.and.news.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.news.data.SharedPrefManager

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun showUser(userNews: String) {
        _text.value = "Welcome $userNews".uppercase()
    }
}