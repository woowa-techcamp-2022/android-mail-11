package com.woowahan.mailapp.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.woowahan.mailapp.databinding.FragmentMailBinding
import com.woowahan.mailapp.model.Mail
import com.woowahan.mailapp.presentation.viewModel.HomeViewModel

class MailFragment : Fragment() {
    private lateinit var binding: FragmentMailBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var mailAdapter: MailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mailAdapter = MailAdapter(createDummyData(viewModel.currentMailType))
        binding.mailRecyclerView.adapter = mailAdapter
    }

    private fun createDummyData(type: String): ArrayList<Mail> {
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
            "",
            "",
            "",
            "Hi Byun, How you doing today? I'm in Chicago now"
        )

        for (i in 0 until names.size) {
            val dummyMail = Mail(names[i], "[${type}] ${title[i]}", contents[i])
            dummy.add(dummyMail)
        }

        return dummy
    }
}