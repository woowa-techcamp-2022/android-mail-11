package com.woowahan.mailapp.presentation.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.woowahan.mailapp.R
import com.woowahan.mailapp.databinding.ActivityHomeBinding
import com.woowahan.mailapp.presentation.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var COLOR_PURPLE = 0
    private var COLOR_GRAY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        COLOR_GRAY = ContextCompat.getColor(this, R.color.gray)
        COLOR_PURPLE = ContextCompat.getColor(this, R.color.purple_500)

        bindingBtns()

        binding.mailBtn.setOnClickListener {
            if (viewModel.currentFragment != HomeViewModel.MAIL) {
                viewModel.currentFragment = HomeViewModel.MAIL
                bindingBtns()
            }
        }

        binding.settingBtn.setOnClickListener {
            if (viewModel.currentFragment != HomeViewModel.SETTING) {
                viewModel.currentFragment = HomeViewModel.SETTING
                bindingBtns()
            }
        }
    }

    private fun bindingBtns() {
        if (viewModel.currentFragment == HomeViewModel.MAIL) {
            binding.mailText.setTextColor(COLOR_PURPLE)
            binding.mailIcon.imageTintList =
                ColorStateList.valueOf(COLOR_PURPLE)
            binding.settingText.setTextColor(COLOR_GRAY)
            binding.settingIcon.imageTintList =
                ColorStateList.valueOf(COLOR_GRAY)
        } else if (viewModel.currentFragment == HomeViewModel.SETTING) {
            binding.settingIcon.imageTintList =
                ColorStateList.valueOf(COLOR_PURPLE)
            binding.settingText.setTextColor(COLOR_PURPLE)
            binding.mailText.setTextColor(COLOR_GRAY)
            binding.mailIcon.imageTintList =
                ColorStateList.valueOf(COLOR_GRAY)
        }
    }
}