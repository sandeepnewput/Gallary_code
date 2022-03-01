package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class PhotoViewHolder(private val binding: ImageItemBinding,private val onItemClicked: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {
        binding.gallaryImage.loadImage(
            uri,
            getProgressDrawable(binding.gallaryImage.context)
        )
        binding.root.setOnClickListener { onItemClicked(uri) }
    }//end of bind method


}//end of ImageViewHolder

class PhotoListAdapter(private var imagesArray: Array<String>, private val onMediaClicked: (String) -> Unit) :
    RecyclerView.Adapter<PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding,onMediaClicked)

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        holder.bind(imagesArray[position])

    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imagesArray.count()
    }

    fun updateImageArray(newImageArray: Array<String>) {
        imagesArray = newImageArray
        notifyDataSetChanged()

    }

}//end of imagelistadapter