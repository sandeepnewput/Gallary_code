package com.example.gallaryapplication.view.view.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage
import kotlinx.android.synthetic.main.image_item.view.*


class ImageViewHolder(private val binding:ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageList: String, imageListArray: Array<String>, position: Int) {
        binding.gallaryImage.loadImage(
            imageList,
            getProgressDrawable(binding.gallaryImage.context)
        )

        binding.imageLayout.setOnClickListener {
            val action = PhotoFragmentDirections.actionPhotoFragmenttoFullImageFragment(
                imageListArray,
                position
            )
            Navigation.findNavController(it).navigate(action)
        }



    }//end of bind method


}//end of ImageViewHolder

class ImageListAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val itemBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        //convert arraylist ot array
        val imageListArray: Array<String> = imageList.toTypedArray()

        //convert array to arrlist
//        val video_list: List<String> = vowels_array.toList()

        holder.bind(imageList[position],imageListArray,position)
        Log.d("adapterlist", "list of images $imageList")

    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateImageList(newImageList: List<String>) {
        imageList.clear()
        imageList.addAll(newImageList)

    }


}//end of imagelistadapter