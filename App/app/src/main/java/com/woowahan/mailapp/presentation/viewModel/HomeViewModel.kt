package com.woowahan.mailapp.presentation.viewModel

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    companion object {
        val MAIL = "mail"
        val SETTING = "setting"
    }

    var currentFragment = MAIL
}