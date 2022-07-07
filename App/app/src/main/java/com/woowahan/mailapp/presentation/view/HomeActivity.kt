package com.woowahan.mailapp.presentation.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

        COLOR_GRAY = ContextCompat.getColor(this, R.color.gray)
        COLOR_PURPLE = ContextCompat.getColor(this, R.color.purple_500)

        binding.homeNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true

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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.homeFrameLayout, mailFragment)
                .commit()
        } else {
            changeFragment()
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
                    // Setting 버튼 누를 시 메일 초기화
                    resetMailType(isNavigationNeedInit = false)

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

    fun resetMailType(isNavigationNeedInit: Boolean) {
        viewModel.currentMailType = HomeViewModel.PRIMARY
        if (isNavigationNeedInit) {
            binding.bottomNavigationView?.selectedItemId = R.id.mailBtn
            binding.navigationRailView?.selectedItemId = R.id.mailBtn
        }
        binding.homeNavigationView.setCheckedItem(R.id.primaryBtn)
        mailFragment.updateMail()
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