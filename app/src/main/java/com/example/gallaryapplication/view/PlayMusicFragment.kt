package com.example.gallaryapplication.view


import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPlayMusicBinding

class PlayMusicFragment : BaseFragment<FragmentPlayMusicBinding>() {

    private val sharedViewModel: MusicSharedViewModel by activityViewModels()

    private var mediaPlayer: MediaPlayer? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayMusicBinding? {
        return FragmentPlayMusicBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playPauseMusic.setOnClickListener {

            if (mediaPlayer?.isPlaying == true) {
                binding.playPauseMusic.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                mediaPlayer?.pause()
            } else {
                binding.playPauseMusic.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                mediaPlayer?.start()
            }
        }

        binding.nextMusic.setOnClickListener {
            releaseMediaPlayer()
            sharedViewModel.onNextMusicClick()
        }

        binding.previousMusic.setOnClickListener {
            releaseMediaPlayer()
            sharedViewModel.onPreviousMusicClick()
        }

        sharedViewModel.currentUri.observe(viewLifecycleOwner) {
            mediaPlayer = create(context, it.toUri()).apply {
                start()
                showMusicProgress(currentPosition, duration)
                setOnCompletionListener {
                    releaseMediaPlayer()
                    sharedViewModel.onCompleteMusic()
                }
            }
        }

    }//end of onViewCreated mehtod

    private fun showMusicProgress(currentDuration: Int, totalDuration: Int) {

        var currentDuration = currentDuration
        var totalDuration = totalDuration

        binding.current.text = sharedViewModel.timeConversion(currentDuration)
        binding.total.text = sharedViewModel.timeConversion(totalDuration)
        binding.musicSeekBar.max = totalDuration

        val handler = Looper.getMainLooper()?.let {
            Handler(it)
        }

        handler?.postDelayed(object : Runnable {
            override fun run() {
                try {
                    currentDuration = mediaPlayer?.currentPosition ?: 0
                    binding.current.text = sharedViewModel.timeConversion(currentDuration)
                    binding.musicSeekBar.progress = currentDuration
                    handler?.postDelayed(this, 1000)
                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                }
            }
        }, 1000)

        binding.musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
                Unit

            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                currentDuration = seekBar.progress
                mediaPlayer?.seekTo(currentDuration)
            }
        })

    }//end of getMusicProgress

    private fun releaseMediaPlayer() {
        mediaPlayer?.apply {
            stop()
            release()
        }
    }

    override fun onDestroyView() {
        releaseMediaPlayer()
        super.onDestroyView()
    }
}// end of playmusicfragment



