package com.woowahan.mailapp.presentation.view

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

    inner class ViewHolder(val binding: MailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mail: Mail) {
            binding.senderNameTextView.text = mail.sender
            binding.mailTitleTextView.text = mail.title
            binding.mailContentTextView.text = mail.content

            if (Pattern.matches("^[a-zA-Z]*$", mail.sender[0].toString())) {
                binding.defaultProfileImageView.visibility = View.GONE
                binding.senderInitialTextView.text = mail.sender[0].toString()
            }
        }
    }
}