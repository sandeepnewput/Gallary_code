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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPlayVideoBinding
import kotlinx.android.synthetic.main.fragment_play_video.*
import kotlinx.coroutines.*
import java.lang.Runnable

class PlayVideoFragment : Fragment() {


    private var _binding:FragmentPlayVideoBinding? = null
    private val binding get() = _binding!!
    var videoUrl: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isPlaying: Boolean = false
        var isVisible: Boolean = false
        arguments?.let {
            var arrayList = PlayVideoFragmentArgs.fromBundle(it).videoarray
            var indexpostion = PlayVideoFragmentArgs.fromBundle(it).indexpostion
            Log.d("video list", "${arrayList}")
            Log.d("position", "${indexpostion}")

            binding.userVideo.setVideoPath(arrayList[indexpostion])
            binding.userVideo.start()


            binding.playvideolayout.setOnClickListener {
                if (isVisible) {
                    binding.prev.visibility = View.VISIBLE
                    binding.pause.visibility = View.VISIBLE
                    binding.next.visibility = View.VISIBLE
                } else {
                    binding.prev.visibility = View.INVISIBLE
                    binding.pause.visibility = View.INVISIBLE
                    binding.next.visibility = View.INVISIBLE
                }
                isVisible = !isVisible
            }


            binding.prev.setOnClickListener {
                if (indexpostion > 0) {
                    indexpostion--
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                } else {
                    indexpostion = arrayList.size - 1
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                }
            }


            binding.pause.setOnClickListener {
                if (isPlaying) {
                    Log.d("patidar", "in if part $isPlaying")
                    binding.pause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                } else {
                    Log.d("patidar", "in else part $isPlaying")
                    binding.pause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                    binding.userVideo.stopPlayback()
                }
                isPlaying = !isPlaying
                Log.d("patidar", "in without if else part $isPlaying")

            }

            binding.next.setOnClickListener {
                if (indexpostion < (arrayList.size - 1)) {
                    indexpostion++
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                } else {
                    indexpostion = 0
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                }
            }


            binding.userVideo.setOnCompletionListener {
                indexpostion++
                if (indexpostion < (arrayList.size)) {
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                } else {
                    indexpostion = 0
                    binding.userVideo.setVideoPath(arrayList[indexpostion])
                    binding.userVideo.start()
                }
            }


        }//end of argumnets block

        binding.userVideo.setOnPreparedListener {
            setVideoProgress()
        }


    }//end of onViewCreated mehtod

    fun timeConversion(value: Int): String? {
        val songTime: String
        val dur = value
        val hrs = dur / 3600000
        val mns = dur / 60000 % 60000
        val scs = dur % 60000 / 1000
        songTime = if (hrs > 0) {
            String.format("%02d:%02d:%02d", hrs, mns, scs)
        } else {
            String.format("%02d:%02d", mns, scs)
        }
        return songTime
    }

    private fun setVideoProgress() {
        //get the video duration
        var current_pos = binding.userVideo.currentPosition
        var total_duration = binding.userVideo.duration

        //display video duration
        binding.total.text = timeConversion(total_duration)
        binding.current.text = timeConversion(current_pos)
        binding.seekbar.setMax(total_duration)


        val handler = Looper.myLooper()?.let { Handler(it) }
        val runnable: Runnable = object : Runnable {
            override fun run() {
                try {
                    current_pos = binding.userVideo.currentPosition
                    binding.current.text = timeConversion(current_pos)
                    binding.seekbar.progress = current_pos
                    handler?.postDelayed(this, 1000)

                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                } catch (e: NullPointerException) {
                    Log.e("exception", "null pointer exception is comming")
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
                binding.userVideo.seekTo(current_pos)
            }
        })
    }//end of setVideoProgress function

}