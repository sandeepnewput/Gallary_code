package com.example.gallaryapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.MediaItemBinding
import com.example.gallaryapplication.model.MediaModel
import com.example.gallaryapplication.util.getProgressDrawable
import com.example.gallaryapplication.util.loadUri

class MediaViewHolder(
    private val binding: MediaItemBinding,
    private val onClickMedia: (MediaModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(mediaModel: MediaModel) {
        binding.mediaUri.loadUri(
            mediaModel.uri,
            getProgressDrawable(binding.mediaUri.context)
        )
        binding.mediaName.text = mediaModel.name
        binding.root.setOnClickListener { onClickMedia(mediaModel) }
    }


}//end of ImageViewHolder

class MediaDiffCallback : DiffUtil.ItemCallback<MediaModel>() {

    override fun areItemsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem == newItem

}//end of ImageDiffCallback

class MediaListAdapter(
    private val onClickMedia: (MediaModel) -> Unit
) : ListAdapter<MediaModel, MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val itemBinding =
            MediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder

}//end of imagelistadapter






