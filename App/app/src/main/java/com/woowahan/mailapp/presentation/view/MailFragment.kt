package com.woowahan.mailapp.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.woowahan.mailapp.databinding.FragmentMailBinding
import com.woowahan.mailapp.model.Mail
import com.woowahan.mailapp.presentation.viewModel.HomeViewModel

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    private val mailAdapter = MailAdapter()
    private val types = arrayOf("primary", "social", "promotions")

    private lateinit var callback: OnBackPressedCallback
    private var backKeyPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mailRecyclerView.adapter = mailAdapter
        updateMail()
    }

    fun updateMail() {
        binding.mailTypeTextView.text = types[viewModel.currentMailType]

        mailAdapter.mails = (createDummyData(viewModel.currentMailType))
        mailAdapter.notifyDataSetChanged()
    }

    private fun createDummyData(typeId: Int): ArrayList<Mail> {
        val dummy = ArrayList<Mail>()

        val names = arrayOf(
            "Google",
            "John",
            "변영무",
            "Martinez",
            "우아한 테크캠프",
            "Slack",
            "Park",
            "김진원",
            "최우형",
            "Steele"
        )

        val title = arrayOf(
            "계정 보안 알림",
            "Title for dummy",
            "통장 사본",
            "bsidfjisdrgodrjoitjr jsirtjiosdjigs dsidg",
            "5기 최종 결과 안내",
            "New Message",
            "Park",
            "메일2",
            "메일3",
            "Hello"
        )

        val contents = arrayOf(
            "다른 기기에서 내 계정으로 로그인 했습니다. 직접한 로그인이 아니라면 여기를 눌러주세요.",
            "Dear Byun, ",
            "통장 사본 첨부",
            "abcdefghijlasefkoasdfofa aoerawokra se",
            "5기 합격하신 것을 진심으로 축하드립니다.",
            "Someone send a message to me. There are 13 unread messages in slack.",
            "hello",
            "nice to ",
            "meet you~",
            "Hi Byun, How you doing today? I'm in Chicago now"
        )

        val shuffledIdx = names.indices.shuffled()

        for (i in 0 until shuffledIdx.size) {
            val dummyMail =
                Mail(
                    names[shuffledIdx[i]],
                    "[${types[typeId]}] ${title[shuffledIdx[i]]}",
                    contents[shuffledIdx[i]],
                    "2022-07-${String.format("%02d", 11 - i)}"
                )
            dummy.add(dummyMail)
        }

        return dummy
    }

    /**
     * Mail Type 이 Primary 일 때, Back button 2번 누르면 앱 종료
     * Mail Type 이 그 외 일때, Primary 로 돌아가기
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Primary 일 때 종료
                if (viewModel.currentMailType == HomeViewModel.PRIMARY) {
                    if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                        backKeyPressedTime = System.currentTimeMillis()
                        Toast.makeText(
                            requireActivity(),
                            "Press Back button again",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    } else {
                        ActivityCompat.finishAffinity(activity!!)
                    }
                } else {
                    (requireActivity() as HomeActivity).resetMailType()
                    updateMail()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}