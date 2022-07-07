package com.woowahan.mailapp.presentation.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.mailapp.databinding.MailItemBinding
import com.woowahan.mailapp.model.Mail
import java.util.regex.Pattern

class MailAdapter(private val mails: List<Mail>) : RecyclerView.Adapter<MailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mails[position])
    }

    override fun getItemCount(): Int {
        return mails.size
    }

    inner class ViewHolder(private val binding: MailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val BG_COLORS = arrayOf(
            Color.rgb(255, 167, 167),
            Color.rgb(209, 178, 255),
            Color.rgb(178, 204, 255),
            Color.rgb(206, 242, 121),
            Color.rgb(255, 178, 245),
            Color.rgb(255, 193, 158)
        )

        fun bind(mail: Mail) {
            binding.senderNameTextView.text = mail.sender
            binding.mailTitleTextView.text = mail.title
            binding.mailContentTextView.text = mail.content

            if (Pattern.matches("^[a-zA-Z]*$", mail.sender[0].toString())) {
                binding.defaultProfileImageView.visibility = View.GONE
                binding.senderInitialTextView.text = mail.sender[0].toString()
                binding.profileCardView.setCardBackgroundColor(BG_COLORS[BG_COLORS.indices.random()])
            }
        }
    }
}