package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class PhotoViewHolder(private val binding: ImageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String, imageArray: Array<String>, position: Int, listener: OnClickListener) {
        binding.gallaryImage.loadImage(
            uri,
            getProgressDrawable(binding.gallaryImage.context)
        )

        binding.root.setOnClickListener {
            listener.onClick(imageArray[position],position)
        }
    }//end of bind method


}//end of ImageViewHolder

class PhotoListAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<PhotoViewHolder>() {

    private lateinit var  listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        //convert arraylist ot array
        val imageArray: Array<String> = imageList.toTypedArray()
        holder.bind(imageList[position], imageArray, position,listener)


    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imageList.count()
    }

    fun updateImageList(newImageList: List<String>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()

    }
    fun setListener(listener: OnClickListener) {
        this.listener = listener
    }


}//end of imagelistadapter