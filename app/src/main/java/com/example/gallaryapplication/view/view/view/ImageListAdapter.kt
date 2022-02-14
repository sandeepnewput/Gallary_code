package com.example.gallaryapplication.view.view.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class ImageViewHolder(private val binding: ImageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String, imageArray: Array<String>, position: Int) {
        binding.gallaryImage.loadImage(
            uri,
            getProgressDrawable(binding.gallaryImage.context)
        )

        binding.root.setOnClickListener {

            Navigation.findNavController(it).navigate(
                PhotoFragmentDirections.actionPhotoFragmenttoFullImageFragment(
                    imageArray,
                    position
                )
            )//end of navigate
        }


    }//end of bind method


}//end of ImageViewHolder

class ImageListAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val itemBinding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        //convert arraylist ot array
        val imageArray: Array<String> = imageList.toTypedArray()

        //convert array to arrlist
//        val video_list: List<String> = vowels_array.toList()

        holder.bind(imageList[position], imageArray, position)
        Log.d("adapterlist", "list of images $imageList")

    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imageList.count()
    }

    fun updateImageList(newImageList: List<String>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()

    }


}//end of imagelistadapter