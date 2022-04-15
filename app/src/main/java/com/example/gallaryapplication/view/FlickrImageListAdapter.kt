package com.example.gallaryapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.FlickrPhotoItemsBinding
import com.example.gallaryapplication.model.PhotoItem
import com.example.gallaryapplication.util.getProgressDrawable
import com.example.gallaryapplication.util.loadUri

class FlickrImageViewHolder(
    private val binding: FlickrPhotoItemsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photoItem: PhotoItem) {
        binding.flickrImage.loadUri(
            "https://live.staticflickr.com/${photoItem.server}/${photoItem.id}_${photoItem.secret}.jpg",
            getProgressDrawable(binding.flickrImage.context)
        )

    }

}//end of ImageViewHolder


class FlickrDiffCallback : DiffUtil.ItemCallback<PhotoItem>() {

    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
        oldItem == newItem

}//end of ImageDiffCallback

class FlickrImageListAdapter() : ListAdapter<PhotoItem, FlickrImageViewHolder>(FlickrDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val itemBinding =
            FlickrPhotoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlickrImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        holder.bind(getItem((position)))
    }//end of onBindViewHolder

}//end of imagelistadapter






