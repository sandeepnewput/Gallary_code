package com.example.gallaryapplication.view.view.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage
import kotlinx.android.synthetic.main.image_item.view.*


class ImageViewHolder(var view: View): RecyclerView.ViewHolder(view){

}

class ImageListAdapter(private val imageList:ArrayList<String>)
    : RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.image_item,parent,false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("adapterlist","list of images $imageList")
        holder.view.gallaryImage.loadImage(imageList[position],
            getProgressDrawable(holder.view.context)
        )
        //convert arraylist ot array
        val imagelistarray: Array<String> = imageList.toTypedArray()

        //convert array to arrlist
//        val video_list: List<String> = vowels_array.toList()

        holder.view.imageLayout.setOnClickListener {
            val action = PhotoFragmentDirections.actionPhotoFragmenttoFullImageFragment(imagelistarray,position)
            Navigation.findNavController(holder.view).navigate(action)
        }

    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateImageList(newImageList:List<String>){
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()

    }


}//end of imagelistadapter