package com.example.gallaryapplication.view.view.view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage
import kotlinx.android.synthetic.main.video_item.view.*

class VideoViewHolder(private val binding: VideoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(videoList: String, videoListArray: Array<String>, position: Int) {

        binding.gallaryVideo.loadImage(
            videoList,
            getProgressDrawable(binding.gallaryVideo.context)
        )

        binding.videoLayout.setOnClickListener {

            Navigation.findNavController(it).navigate(
                VideoFragmentDirections.actionvideoFragmenttoPlayvideoFragment(
                    videoListArray,
                    position
                )
            )//end of navigate

        }


    }//end of bind function


}

class VideoListAdapter(
    private val videoList: ArrayList<String>

) : RecyclerView.Adapter<VideoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        Log.d("adapterlist", "list of images $videoList")
        //convert arraylist ot array
        val videoListArray: Array<String> = videoList.toTypedArray()

        holder.bind(videoList[position], videoListArray, position)

    }//end of onBindViewHolder mehtod

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateVideoList(newVideoList: List<String>) {
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()

    }

}//end of VideoListAdapter