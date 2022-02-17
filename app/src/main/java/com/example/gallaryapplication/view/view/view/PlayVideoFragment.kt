package com.example.gallaryapplication.view.view.view

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import com.example.gallaryapplication.databinding.FragmentPhotoBinding
import com.example.gallaryapplication.databinding.FragmentPlayVideoBinding
import java.lang.Runnable

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding>() {

    private lateinit var videoList: List<String>
    private var indexPosition: Int = 0

    private val viewModel by lazy {
        ViewModelProvider(this, PlayVideoViewModelFactory(videoList, indexPosition))
            .get(PlayVideoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoList = PlayVideoFragmentArgs.fromBundle(it).videoarray.toList()
            indexPosition = PlayVideoFragmentArgs.fromBundle(it).indexpostion
        }

    }//end of onCreate method

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayVideoBinding? {
        return FragmentPlayVideoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playVideoLayout.setOnClickListener {
            viewModel.toggleControlButton()
        }//end of setOnClickListener


        binding.previousVideo.setOnClickListener {
            viewModel.onPreviousVideoClick()
        }//end of setOnClickListener


        binding.pauseVideo.setOnClickListener {
            viewModel.playPauseVideo()
        }//end of setOnClickListener


        binding.nextVideo.setOnClickListener {
            viewModel.onNextVideoClick()
        }//end of setOnClickListener


        binding.galleryVideo.setOnCompletionListener {
            viewModel.onCompleteVideo()
        }//end of setOnCompletionListener


        binding.galleryVideo.setOnPreparedListener {
            setVideoProgress()
        }//end of setOnPreparedListener


        viewModel.currentUri.observe(viewLifecycleOwner) { videoUri ->
            binding.galleryVideo.setVideoPath(videoUri)
            binding.galleryVideo.start()
        }

        viewModel.showControlButton.observe(viewLifecycleOwner) { isVisible ->
            binding.previousVideo.isVisible = isVisible
            binding.pauseVideo.isVisible = isVisible
            binding.nextVideo.isVisible = isVisible
        }

        viewModel.isPlayPauseVideo.observe(viewLifecycleOwner) { isPlayPause ->
            if (isPlayPause) {
                binding.pauseVideo.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                binding.galleryVideo.setVideoPath(videoList[indexPosition])
                binding.galleryVideo.start()
            } else {
                binding.pauseVideo.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                binding.galleryVideo.stopPlayback()
            }
        }



    }//end of onViewCreated mehtod

    private fun setVideoProgress() {
        //get the video duration
        var current_pos = binding.galleryVideo.currentPosition
        var total_duration = binding.galleryVideo.duration

        //display video duration
        binding.total.text = viewModel.timeConversion(total_duration)
        binding.current.text = viewModel.timeConversion(current_pos)
        binding.seekbar.setMax(total_duration)


        val handler = Looper.myLooper()?.let { Handler(it) }
        val runnable: Runnable = object : Runnable {
            override fun run() {
                try {
                    current_pos = binding.galleryVideo.currentPosition
                    binding.current.text = viewModel.timeConversion(current_pos)
                    binding.seekbar.progress = current_pos
                    handler?.postDelayed(this, 1000)

                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }
        }
        handler?.postDelayed(runnable, 1000)

        //seekbar change listner
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                current_pos = seekBar.progress
                binding.galleryVideo.seekTo(current_pos)
            }
        })
    }//end of setVideoProgress function



}