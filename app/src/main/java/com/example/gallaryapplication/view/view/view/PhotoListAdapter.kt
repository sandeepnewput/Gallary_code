package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.model.MediaModel
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class PhotoViewHolder(
    private val binding: ImageItemBinding,
    private val onClickMedia: (MediaModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(mediaModel: MediaModel) {
        binding.gallaryImage.loadImage(
            mediaModel.uri,
            getProgressDrawable(binding.gallaryImage.context)
        )
        binding.galleryImageName.text = mediaModel.name
        binding.root.setOnClickListener { onClickMedia(mediaModel) }
    }


}//end of ImageViewHolder


class ImageDiffCallback : DiffUtil.ItemCallback<MediaModel>() {

    override fun areItemsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem.uri == newItem.uri

    override fun areContentsTheSame(oldItem: MediaModel, newItem: MediaModel) =
        oldItem == newItem

}//end of ImageDiffCallback

class PhotoListAdapter(
    private val onClickMedia: (MediaModel) -> Unit
) : ListAdapter<MediaModel, PhotoViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder

}//end of imagelistadapter






