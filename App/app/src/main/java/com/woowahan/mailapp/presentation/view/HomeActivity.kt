package com.woowahan.mailapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView
import com.woowahan.mailapp.R
import com.woowahan.mailapp.databinding.ActivityHomeBinding
import com.woowahan.mailapp.presentation.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var COLOR_PURPLE = 0
    private var COLOR_GRAY = 0

    private lateinit var mailFragment: MailFragment
    private lateinit var settingFragment: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mailFragment = MailFragment()
        settingFragment = SettingFragment()

        supportFragmentManager.beginTransaction().add(R.id.homeFrameLayout, mailFragment)
            .commit()

        if (savedInstanceState != null) {
            supportFragmentManager.beginTransaction().remove(mailFragment)
            changeFragment()
            if (viewModel.currentFragment == HomeViewModel.SETTING) {
                binding.bottomNavigationView?.selectedItemId = R.id.settingBtn
                binding.navigationRailView?.selectedItemId = R.id.settingBtn
            }
        }

        COLOR_GRAY = ContextCompat.getColor(this, R.color.gray)
        COLOR_PURPLE = ContextCompat.getColor(this, R.color.purple_500)

        binding.homeNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true

            viewModel.currentFragment = HomeViewModel.MAIL
            changeFragment()

            when (it.itemId) {
                R.id.primaryBtn -> viewModel.currentMailType = HomeViewModel.PRIMARY
                R.id.socialBtn -> viewModel.currentMailType = HomeViewModel.SOCIAL
                R.id.promotionsBtn -> viewModel.currentMailType = HomeViewModel.PROMOTIONS
            }

            changeMailType()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.homeNavigationView.bringToFront()

        binding.bottomNavigationView?.setOnItemSelectedListener(navigationOnSelectedListener)

        binding.navigationRailView?.setOnItemSelectedListener(navigationOnSelectedListener)

        binding.drawerBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private val navigationOnSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mailBtn -> {
                    viewModel.currentFragment = HomeViewModel.MAIL
                    changeFragment()
                    true
                }
                R.id.settingBtn -> {
                    viewModel.currentFragment = HomeViewModel.SETTING
                    changeFragment()
                    true
                }
            }
            true
        }

    fun changeMailType() {
        binding.bottomNavigationView?.selectedItemId = R.id.mailBtn
        binding.navigationRailView?.selectedItemId = R.id.mailBtn
        mailFragment.updateMail()
    }

    fun resetMailType() {
        viewModel.currentMailType = HomeViewModel.PRIMARY
        binding.homeNavigationView.setCheckedItem(R.id.primaryBtn)
    }

    fun changeFragment() {
        binding.fragmentNameTextView.text = viewModel.currentFragment

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

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}