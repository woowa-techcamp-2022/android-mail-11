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
            // 화면 회전 시 기존 Fragment 유지 및 Navigation button 유지
            changeFragment()
            if (viewModel.currentFragment == HomeViewModel.SETTING) {
                binding.bottomNavigationView?.selectedItemId = R.id.settingBtn
                binding.navigationRailView?.selectedItemId = R.id.settingBtn
            }
        }

        binding.homeNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            // Mail Fragment로 변경
            viewModel.currentFragment = HomeViewModel.MAIL
            changeFragment()

            when (it.itemId) {
                R.id.primaryBtn -> viewModel.currentMailType = HomeViewModel.PRIMARY
                R.id.socialBtn -> viewModel.currentMailType = HomeViewModel.SOCIAL
                R.id.promotionsBtn -> viewModel.currentMailType = HomeViewModel.PROMOTIONS
            }

            // Mail type 변경
            changeMailType()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.homeNavigationView.bringToFront()

        binding.bottomNavigationView?.setOnItemSelectedListener(navigationOnSelectedListener)
        binding.navigationRailView?.setOnItemSelectedListener(navigationOnSelectedListener)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * Navigation Menu 클릭 시 해당 Fragment 로 이동
     */
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

    /**
     * Navigation Drawer 메뉴 클릭 시 메일 변경하는 함수
     */
    private fun changeMailType() {
        binding.bottomNavigationView?.selectedItemId = R.id.mailBtn
        binding.navigationRailView?.selectedItemId = R.id.mailBtn
        mailFragment.updateMail()
    }

    /**
     * Setting Fragment 로 넘어갈 때,
     * Setting Fragment 에서 Back button을 눌렀을 때
     * 메일 타입 초기화하는 함수
     */
    fun resetMailType() {
        viewModel.currentMailType = HomeViewModel.PRIMARY
        binding.homeNavigationView.setCheckedItem(R.id.primaryBtn)
    }


    /**
     * ViewModel 의 currentFragment 정보에 따라 Fragment 교체
     */
    fun changeFragment() {
        binding.topAppBar.title = viewModel.currentFragment

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
        // Drawer 가 열려있으면 Back button 으로 닫음
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}