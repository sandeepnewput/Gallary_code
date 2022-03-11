package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class PhotoViewHolder(
    private val binding: ImageItemBinding,
    private val onClickMedia: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {
        binding.gallaryImage.loadImage(
            uri,
            getProgressDrawable(binding.gallaryImage.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind method


}//end of ImageViewHolder

class PhotoListAdapter(
    private var imageList: List<String>,
    private val onClickMedia: (String) -> Unit
) :
    RecyclerView.Adapter<PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding, onClickMedia)

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        holder.bind(imageList[position])

    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imageList.count()
    }

    fun updateImageList(newImageList: List<String>) {
        imageList = newImageList
        notifyDataSetChanged()

    }

}//end of imagelistadapter