package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class VideoViewHolder(
    private val binding: VideoItemBinding,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {
        binding.gallaryVideo.loadImage(
            uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind function


}// end of VideoViewHolder

class VideoDiffCallback :
    DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

}//end of ImageDiffCallback


class VideoListAdapter(
    private val onClickMedia: (String) -> Unit
) : ListAdapter<String, VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder mehtod

}//end of VideoListAdapter


