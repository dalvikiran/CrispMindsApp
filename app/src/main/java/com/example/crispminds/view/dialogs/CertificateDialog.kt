package com.example.crispminds.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.crispminds.R
import com.example.crispminds.databinding.CertificateAndAwardCellLayoutBinding
import com.example.crispminds.databinding.CertificateDialogLayoutBinding
import com.example.crispminds.databinding.VideoDialogLayoutBinding
import com.example.crispminds.models.CertificationAndAwards
import com.example.crispminds.models.VideoDTO
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class CertificateDialog(certificateDetails : CertificationAndAwards) : DialogFragment() {

    lateinit var binding : CertificateDialogLayoutBinding
    val certificateDetails : CertificationAndAwards

    init {
        this.certificateDetails = certificateDetails
        setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
            R.layout.certificate_dialog_layout, null, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.certificateDto = certificateDetails

        binding.closeClickImageView.setOnClickListener{
            dismiss()
        }

    }

}