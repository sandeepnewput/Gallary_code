package com.example.gallaryapplication.view.view.view



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class VideoViewHolder(private val binding: VideoItemBinding,private val onClickMedia: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {

        binding.gallaryVideo.loadImage(
            uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind function


}

class VideoListAdapter(
    private var videoList: List<String>,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.Adapter<VideoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding,onClickMedia)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }//end of onBindViewHolder mehtod

    override fun getItemCount(): Int {
        return videoList.count()
    }

    fun updateVideoList(newVideoList: List<String>) {
        videoList = newVideoList
        notifyDataSetChanged()
    }



}//end of VideoListAdapter


