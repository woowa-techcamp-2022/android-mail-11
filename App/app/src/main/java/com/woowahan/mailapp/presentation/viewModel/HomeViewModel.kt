package com.woowahan.mailapp.presentation.viewModel

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    companion object {
        val MAIL = "mail"
        val SETTING = "setting"

        val PRIMARY = "primary"
        val SOCIAL = "social"
        val PROMOTIONS = "promotions"
    }

    var currentFragment = MAIL
    var currentMailType = PRIMARY
}