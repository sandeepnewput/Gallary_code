package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.FlickrPhotoItemsBinding
import com.example.gallaryapplication.view.view.model.PhotoContainer
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class FlickrImageViewHolder(
    private val binding: FlickrPhotoItemsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photoContainer:  PhotoContainer) {
        binding.flickrImage.loadImage(
            photoContainer.url,
            getProgressDrawable(binding.flickrImage.context)
        )

    }

}//end of ImageViewHolder


class FlickrDiffCallback : DiffUtil.ItemCallback<PhotoContainer>() {

    override fun areItemsTheSame(oldItem: PhotoContainer, newItem: PhotoContainer) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotoContainer, newItem: PhotoContainer) =
        oldItem == newItem

}//end of ImageDiffCallback

class FlickrImageListAdapter() : ListAdapter<PhotoContainer, FlickrImageViewHolder>(FlickrDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val itemBinding =
            FlickrPhotoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlickrImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder

}//end of imagelistadapter






