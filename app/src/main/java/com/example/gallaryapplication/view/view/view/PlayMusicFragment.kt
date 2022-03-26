package com.example.gallaryapplication.view.view.view

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPlayMusicBinding
import com.example.gallaryapplication.databinding.FragmentPlayVideoBinding
import java.lang.Runnable

class PlayMusicFragment : BaseFragment<FragmentPlayMusicBinding>() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayMusicBinding? {
        return FragmentPlayMusicBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playMusicLayout.setOnClickListener {
            viewModel.toggleControlButton()
        }//end of setOnClickListener

        binding.previousMusic.setOnClickListener {
            viewModel.onPreviousVideoClick()
        }//end of setOnClickListener

        binding.pauseMusic.setOnClickListener {
            viewModel.playPauseVideo()
        }//end of setOnClickListener

        binding.nextMusic.setOnClickListener {
            viewModel.onNextVideoClick()
        }//end of setOnClickListener

        binding.galleryMusic.setOnCompletionListener {
            viewModel.onCompleteVideo()
        }//end of setOnCompletionListener

        binding.galleryMusic.setOnPreparedListener {
//            setVideoProgress()

        }//end of setOnPreparedListene

        viewModel.currentUri.observe(viewLifecycleOwner) { videoUri ->
            MediaPlayer().setDataSource(videoUri)
            MediaPlayer().start()
        }

    }//end of onViewCreated mehtod
}