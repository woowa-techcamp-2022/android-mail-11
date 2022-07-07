package com.woowahan.mailapp.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.woowahan.mailapp.R
import com.woowahan.mailapp.databinding.FragmentSettingBinding
import com.woowahan.mailapp.model.ME
import com.woowahan.mailapp.presentation.viewModel.HomeViewModel

class SettingFragment : Fragment() {
    private lateinit var callback: OnBackPressedCallback
    private var backKeyPressedTime: Long = 0

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailTextView.text = ME.email
        binding.nickNameTextView.text = ME.nickname
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.currentFragment = HomeViewModel.MAIL
                (requireActivity() as HomeActivity).resetMailType(isNavigationNeedInit = true)
                (requireActivity() as HomeActivity).changeFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}