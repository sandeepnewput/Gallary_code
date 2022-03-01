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
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPlayVideoBinding
import java.lang.Runnable

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding>() {


    private val viewModel: SharedViewModel by activityViewModels()

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
//                binding.galleryVideo.setVideoPath(videoList[indexPosition])
                binding.galleryVideo.start()
            } else {
                binding.pauseVideo.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                binding.galleryVideo.stopPlayback()
            }
        }



    }//end of onViewCreated mehtod

    private fun setVideoProgress() {
        //get the video duration
        var currentPosition = binding.galleryVideo.currentPosition
        var totalDuration = binding.galleryVideo.duration

        //display video duration
        binding.total.text = viewModel.timeConversion(totalDuration)
        binding.current.text = viewModel.timeConversion(currentPosition)
        binding.seekbar.setMax(totalDuration)


        val handler = Looper.myLooper()?.let { Handler(it) }
        val runnable: Runnable = object : Runnable {
            override fun run() {
                try {
                    currentPosition = binding.galleryVideo.currentPosition
                    binding.current.text = viewModel.timeConversion(currentPosition)
                    binding.seekbar.progress = currentPosition
                    handler?.postDelayed(this,1000)

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
            override fun onStartTrackingTouch(seekBar: SeekBar){}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                currentPosition = seekBar.progress
                binding.galleryVideo.seekTo(currentPosition)
            }
        })
    }//end of setVideoProgress function

    override fun backPressed() {
//        findNavController().navigate(R.id.videoFragment)
        findNavController().navigate(R.id.action_global_bottomNavigationView)
    }


}