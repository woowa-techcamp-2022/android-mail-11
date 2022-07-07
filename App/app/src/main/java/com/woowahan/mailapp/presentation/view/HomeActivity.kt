package com.woowahan.mailapp.presentation.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
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

    private val mailFragment = MailFragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        COLOR_GRAY = ContextCompat.getColor(this, R.color.gray)
        COLOR_PURPLE = ContextCompat.getColor(this, R.color.purple_500)

        bindingBottomBtns()
        bindingDrawerBtns()
        binding.homeNavigationView.itemIconTintList = null
        binding.homeNavigationView.bringToFront()

        binding.primaryBtn.setOnClickListener {
            viewModel.currentMailType = HomeViewModel.PRIMARY
            changeMailType()
        }

        binding.socialBtn.setOnClickListener {
            viewModel.currentMailType = HomeViewModel.SOCIAL
            changeMailType()
        }

        binding.promotionsBtn.setOnClickListener {
            viewModel.currentMailType = HomeViewModel.PROMOTIONS
            changeMailType()
        }

        binding.mailBtn?.setOnClickListener {
            if (viewModel.currentFragment != HomeViewModel.MAIL) {
                viewModel.currentFragment = HomeViewModel.MAIL
                changeFragment()
            }
        }

        binding.settingBtn?.setOnClickListener {
            if (viewModel.currentFragment != HomeViewModel.SETTING) {
                viewModel.currentFragment = HomeViewModel.SETTING
                changeFragment()
            }
        }

        binding.navigationRailView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mailBtn -> {
                    viewModel.currentFragment = HomeViewModel.MAIL
                    changeFragment()
                    true
                }
                R.id.settingBtn -> {
                    viewModel.currentFragment = HomeViewModel.SETTING
                    viewModel.currentMailType = HomeViewModel.PRIMARY
                    changeFragment()
                    true
                }
                else -> false
            }
        }

        binding.drawerBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.homeFrameLayout, mailFragment)
                .commit()
        } else {
            changeFragment()
        }
    }

    fun changeMailType() {
        bindingDrawerBtns()
        binding.navigationRailView?.selectedItemId = R.id.mailBtn
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        mailFragment.updateMail()

        if (viewModel.currentFragment == HomeViewModel.SETTING) {
            viewModel.currentFragment = HomeViewModel.MAIL
            changeFragment()
        }
    }

    fun changeFragment() {
        binding.fragmentNameTextView.text = viewModel.currentFragment
        bindingBottomBtns()

        if (viewModel.currentFragment == HomeViewModel.MAIL) {
            supportFragmentManager.beginTransaction().replace(
                R.id.homeFrameLayout, mailFragment
            ).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(
                R.id.homeFrameLayout, settingFragment
            ).commit()
        }
    }

    fun bindingDrawerBtns() {
        val buttons = arrayOf(binding.primaryBtn, binding.socialBtn, binding.promotionsBtn)

        for (idx in buttons.indices) {
            if (idx == viewModel.currentMailType) {
                buttons[idx].setBackgroundResource(R.drawable.home_drawer_btn_selected)
                buttons[idx].compoundDrawableTintList =
                    ColorStateList.valueOf(COLOR_PURPLE)
                buttons[idx].setTextColor(COLOR_PURPLE)
            } else {
                buttons[idx].setBackgroundResource(R.drawable.home_drawer_btn_unselected)
                buttons[idx].compoundDrawableTintList =
                    ColorStateList.valueOf(COLOR_GRAY)
                buttons[idx].setTextColor(COLOR_GRAY)
            }
        }
    }

    private fun bindingBottomBtns() {
        if (viewModel.currentFragment == HomeViewModel.MAIL) {
            binding.mailText?.setTextColor(COLOR_PURPLE)
            binding.mailIcon?.imageTintList =
                ColorStateList.valueOf(COLOR_PURPLE)
            binding.settingText?.setTextColor(COLOR_GRAY)
            binding.settingIcon?.imageTintList =
                ColorStateList.valueOf(COLOR_GRAY)
        } else if (viewModel.currentFragment == HomeViewModel.SETTING) {
            binding.settingIcon?.imageTintList =
                ColorStateList.valueOf(COLOR_PURPLE)
            binding.settingText?.setTextColor(COLOR_PURPLE)
            binding.mailText?.setTextColor(COLOR_GRAY)
            binding.mailIcon?.imageTintList =
                ColorStateList.valueOf(COLOR_GRAY)
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}