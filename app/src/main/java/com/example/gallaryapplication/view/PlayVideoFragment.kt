package com.example.gallaryapplication.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPlayVideoBinding
import java.lang.Runnable

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding>() {

    private val sharedViewModel: MediaSharedViewModel by activityViewModels()

    private val viewModel:PlayVideoViewModel by viewModels()

    private lateinit var uri:String

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
            sharedViewModel.onPreviousVideoClick()
        }//end of setOnClickListener

        binding.playPauseVideo.setOnClickListener {

            if(binding.galleryVideo.isPlaying){
                binding.playPauseVideo.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                binding.galleryVideo.stopPlayback()
            }else{
                binding.playPauseVideo.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                binding.galleryVideo.setVideoPath(uri)
                binding.galleryVideo.start()
            }
        }//end of setOnClickListener

        binding.nextVideo.setOnClickListener {
            sharedViewModel.onNextVideoClick()
        }//end of setOnClickListener

        binding.galleryVideo.setOnCompletionListener {
            sharedViewModel.onCompleteVideo()
        }//end of setOnCompletionListener

        binding.galleryVideo.setOnPreparedListener {
            setVideoProgress()
        }//end of setOnPreparedListener

        sharedViewModel.currentUri.observe(viewLifecycleOwner) { videoUri ->
            uri = videoUri
            binding.galleryVideo.setVideoPath(videoUri)
            binding.galleryVideo.start()
        }

        viewModel.showControlButton.observe(viewLifecycleOwner) { isVisible ->
            binding.previousVideo.isVisible = isVisible
            binding.playPauseVideo.isVisible = isVisible
            binding.nextVideo.isVisible = isVisible
        }

    }//end of onViewCreated mehtod

    private fun setVideoProgress() {

        var currentPosition = binding.galleryVideo.currentPosition
        var totalDuration = binding.galleryVideo.duration

        binding.current.text = sharedViewModel.timeConversion(currentPosition)
        binding.total.text = sharedViewModel.timeConversion(totalDuration)
        binding.seekbar.max = totalDuration

        val handler = Looper.myLooper()?.let { Handler(it) }

        handler?.postDelayed(object : Runnable {
            override fun run() {
                try {
                    currentPosition = binding.galleryVideo.currentPosition
                    binding.current.text = sharedViewModel.timeConversion(currentPosition)
                    binding.seekbar.progress = currentPosition
                    handler?.postDelayed(this, 1000)
                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                }catch (e:NullPointerException){
                    e.printStackTrace()
                }
            }
        },1000)

        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) =
                Unit

            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                currentPosition = seekBar.progress
                binding.galleryVideo.seekTo(currentPosition)
            }
        })
    }//end of setVideoProgress function

}