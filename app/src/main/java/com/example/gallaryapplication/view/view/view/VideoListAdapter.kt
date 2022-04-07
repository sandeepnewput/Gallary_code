package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.model.MediaModel
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class VideoViewHolder(
    private val binding: VideoItemBinding,
    private val onClickMedia: (MediaModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mediaModel: MediaModel) {
        binding.gallaryVideo.loadImage(
            mediaModel.uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )
        binding.galleryVideoName.text = mediaModel.name
        binding.root.setOnClickListener { onClickMedia(mediaModel) }
    }


}// end of VideoViewHolder

class VideoDiffCallback :
    DiffUtil.ItemCallback<MediaModel>() {

    override fun areItemsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem == newItem

}//end of ImageDiffCallback


class VideoListAdapter(
    private val onClickMedia: (MediaModel) -> Unit
) : ListAdapter<MediaModel, VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder mehtod

}//end of VideoListAdapter


