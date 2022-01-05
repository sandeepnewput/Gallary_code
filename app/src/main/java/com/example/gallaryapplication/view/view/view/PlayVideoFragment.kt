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
import kotlinx.android.synthetic.main.fragment_play_video.*
import kotlinx.coroutines.*
import java.lang.Runnable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayVideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


//    lateinit var arrayList : Array<String>
//    var indexpostion: Int? = null
    var videoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_video, container, false)
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

            userVideo.setVideoPath(arrayList[indexpostion])
            userVideo.start()


            playvideolayout.setOnClickListener {
                if (isVisible) {
                    prev.visibility = View.VISIBLE
                    pause.visibility = View.VISIBLE
                    next.visibility = View.VISIBLE
                } else {
                    prev.visibility = View.INVISIBLE
                    pause.visibility = View.INVISIBLE
                    next.visibility = View.INVISIBLE
                }
                isVisible = !isVisible
            }


            prev.setOnClickListener {
                if (indexpostion > 0) {
                    indexpostion--
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                } else {
                    indexpostion = arrayList.size - 1
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                }
            }


            pause.setOnClickListener {
                if (isPlaying) {
                    Log.d("patidar", "in if part $isPlaying")
                    pause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                } else {
                    Log.d("patidar", "in else part $isPlaying")
                    pause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                    userVideo.stopPlayback()
                }
                isPlaying = !isPlaying
                Log.d("patidar", "in without if else part $isPlaying")

            }

            next.setOnClickListener {
                if (indexpostion < (arrayList.size - 1)) {
                    indexpostion++
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                } else {
                    indexpostion = 0
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                }
            }


            userVideo.setOnCompletionListener {
                indexpostion++
                if (indexpostion < (arrayList.size)) {
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                } else {
                    indexpostion = 0
                    userVideo.setVideoPath(arrayList[indexpostion])
                    userVideo.start()
                }
            }




        }//end of argumnets block


        userVideo.setOnPreparedListener {

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
    private  fun setVideoProgress() {
        //get the video duration
        var current_pos = userVideo.currentPosition
        var total_duration = userVideo.duration

        //display video duration
        total.text = timeConversion(total_duration)
        current.text = timeConversion(current_pos)
        seekbar.setMax(total_duration)






        val handler = Looper.myLooper()?.let { Handler(it) }
        val runnable: Runnable = object : Runnable {
            override fun run() {
                try {
                    current_pos = userVideo.currentPosition
                    current.text = timeConversion(current_pos)
                    seekbar.progress = current_pos
                    handler?.postDelayed(this, 1000)

                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                }catch (e:NullPointerException){
                    Log.e("exception","null pointer exception is comming")
                    e.printStackTrace()
                }
            }
        }
       handler?.postDelayed(runnable, 1000)

        //seekbar change listner
        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                current_pos = seekBar.progress
                userVideo.seekTo(current_pos)
            }
        })
    }//end of setVideoProgress function



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayVideoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayVideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}