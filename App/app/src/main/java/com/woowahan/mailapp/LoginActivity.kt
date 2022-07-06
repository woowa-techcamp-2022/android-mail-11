package com.woowahan.mailapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.woowahan.mailapp.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.nicknameEditText.addTextChangedListener(nicknameWatcher)
    }

    private val nicknameWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val nickname = binding.nicknameEditText.text.toString()

            if (isValidNickname(nickname)) {
                binding.nextBtn.isEnabled = true
                binding.nicknameInputLayout.isEndIconVisible = false
                binding.nicknameInputLayout.error = null

            } else {
                binding.nextBtn.isEnabled = false
                binding.nicknameInputLayout.isEndIconVisible = true
                binding.nicknameInputLayout.error = "닉네임은 영문과 숫자를 결합한 4~12자로 입력해주세요."
            }
        }

    }

    private fun isValidNickname(nickname: String): Boolean {
        val nicknameRegex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{4,12}$"
        val pattern = Pattern.compile(nicknameRegex)

        return pattern.matcher(nickname).find()
    }
}