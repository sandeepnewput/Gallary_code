package com.example.gallaryapplication.view.view.view



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class VideoViewHolder(private val binding: VideoItemBinding,private val onMediaClicked: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {

        binding.gallaryVideo.loadImage(
            uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )
        binding.root.setOnClickListener { onMediaClicked(uri) }
    }//end of bind function


}

class VideoListAdapter(
    private var videosArray: Array<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<VideoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {

        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding,onItemClicked)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videosArray[position])
    }//end of onBindViewHolder mehtod

    override fun getItemCount(): Int {
        return videosArray.count()
    }

    fun updateVideoArray(newVideoArray: Array<String>) {
        videosArray = newVideoArray
        notifyDataSetChanged()
    }



}//end of VideoListAdapter


