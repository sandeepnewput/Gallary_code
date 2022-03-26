package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.*
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.model.AudioModel
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

private const val ARG_DONE = "uri"

class PhotoViewHolder(
    private val binding: ImageItemBinding,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun update(bundle: Bundle) {
        if (bundle.containsKey(ARG_DONE)) {
            val uri = bundle.getString(ARG_DONE)
            binding.gallaryImage.loadImage(
                uri,
                getProgressDrawable(binding.gallaryImage.context)
            )
        }
    }

    fun bind(uri: String) {
        binding.gallaryImage.loadImage(
            uri,
            getProgressDrawable(binding.gallaryImage.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind method

}//end of ImageViewHolder


class ImageDiffCallback :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) =
        oldItem.toUri() === newItem.toUri()

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    override fun getChangePayload(oldItem: String, newItem: String): Any? {

        if (oldItem == newItem) {
            return if (oldItem == newItem) {
                super.getChangePayload(oldItem, newItem)
            } else {

                val diff = Bundle()
                diff.putString(ARG_DONE, newItem.toUri().toString())
                return diff
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }

}//end of ImageDiffCallback

class PhotoListAdapter(
    private val onClickMedia: (String) -> Unit
) : ListAdapter<String, PhotoViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }//end of onBindViewHolder

    override fun onBindViewHolder(holder: PhotoViewHolder, pos: Int, payload: List<Any>) {

        if (payload.isEmpty() || payload[0] !is Bundle) {
            holder.bind(getItem(pos))
        } else {
            val bundle = payload[0] as Bundle
            holder.update(bundle)
        }
    }

}//end of imagelistadapter






