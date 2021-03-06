package com.woowahan.mailapp.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.woowahan.mailapp.R
import com.woowahan.mailapp.databinding.ActivityLoginBinding
import com.woowahan.mailapp.model.ME
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.nicknameEditText.addTextChangedListener(nicknameWatcher)
        binding.emailEditText.addTextChangedListener(emailWatcher)

        binding.nextBtn.setOnClickListener {
            ME.nickname = binding.nicknameEditText.text.toString()
            ME.email = binding.emailEditText.text.toString()

            val nextIntent = Intent(this, HomeActivity::class.java)
            nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(nextIntent)
        }
    }

    /**
     * Nickname 이 Valid 한 지 검사
     * Nickname 이 비어 있다면 error 제거
     */
    private val nicknameWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val nickname = binding.nicknameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (isValidNickname(nickname)) {
                binding.nicknameInputLayout.isEndIconVisible = false
                binding.nicknameInputLayout.error = null
            } else if (s != null) {
                if (s.isEmpty()) {
                    binding.nicknameInputLayout.isEndIconVisible = false
                    binding.nicknameInputLayout.error = null
                } else {
                    binding.nicknameInputLayout.isEndIconVisible = true
                    binding.nicknameInputLayout.error = "닉네임은 영문과 숫자를 결합한 4~12자로 입력해주세요."
                }
            }

            binding.nextBtn.isEnabled =
                isValidNickname(nickname) && isValidEmail(email)
        }

    }

    /**
     * Email 이 Valid 한 지 검사
     * Email 이 비어있다면 error 제거
     */
    private val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val nickname = binding.nicknameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (isValidEmail(email)) {
                binding.emailInputLayout.isEndIconVisible = false
                binding.emailInputLayout.error = null
            } else if (s != null) {
                if (s.isEmpty()) {
                    binding.emailInputLayout.isEndIconVisible = false
                    binding.emailInputLayout.error = null
                } else {
                    binding.emailInputLayout.isEndIconVisible = true
                    binding.emailInputLayout.error = "올바른 이메일 형식이 아닙니다."
                }
            }

            binding.nextBtn.isEnabled =
                isValidNickname(nickname) && isValidEmail(email)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = android.util.Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).find()
    }


    private fun isValidNickname(nickname: String): Boolean {
        val nicknameRegex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{4,12}$"
        val pattern = Pattern.compile(nicknameRegex)

        return pattern.matcher(nickname).find()
    }
}