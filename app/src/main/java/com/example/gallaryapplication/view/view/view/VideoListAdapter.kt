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

    fun bind(uri: String, videoArray: Array<String>, position: Int) {

        binding.gallaryVideo.loadImage(
            uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )

        binding.root.setOnClickListener {

            Navigation.findNavController(it).navigate(
                VideoFragmentDirections.actionvideoFragmenttoPlayvideoFragment(
                    videoArray,
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
        val videoArray: Array<String> = videoList.toTypedArray()

        holder.bind(videoList[position], videoArray, position)

    }//end of onBindViewHolder mehtod

    override fun getItemCount(): Int {
        return videoList.count()
    }

    fun updateVideoList(newVideoList: List<String>) {
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()

    }

}//end of VideoListAdapter