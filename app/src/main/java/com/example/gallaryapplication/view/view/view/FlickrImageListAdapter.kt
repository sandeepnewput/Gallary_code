package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.FlickrPhotoItemsBinding
import com.example.gallaryapplication.view.view.model.Photos
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class FlickrImageViewHolder(
    private val binding: FlickrPhotoItemsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photos:  Photos) {
        binding.flickrImage.loadImage(
            "https://live.staticflickr.com/${photos.server}/${photos.id}_${photos.secret}.jpg",
            getProgressDrawable(binding.flickrImage.context)
        )

    }

}//end of ImageViewHolder


class FlickrDiffCallback : DiffUtil.ItemCallback<Photos>() {

    override fun areItemsTheSame(oldItem: Photos, newItem: Photos) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photos, newItem: Photos) =
        oldItem == newItem

}//end of ImageDiffCallback

class FlickrImageListAdapter() : ListAdapter<Photos, FlickrImageViewHolder>(FlickrDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val itemBinding =
            FlickrPhotoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlickrImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }//end of onBindViewHolder

}//end of imagelistadapter






