package com.woowahan.mailapp.presentation.viewModel

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    companion object {
        val MAIL = "Mail"
        val SETTING = "Setting"

        val PRIMARY = 0
        val SOCIAL = 1
        val PROMOTIONS = 2
    }

    var currentFragment = MAIL
    var currentMailType = PRIMARY
}