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
import kotlinx.android.synthetic.main.video_item.view.*

class VideoViewHolder(var view: View): RecyclerView.ViewHolder(view){

}

class VideoListAdapter(private val videoList:ArrayList<String>

) : RecyclerView.Adapter<VideoViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.video_item,parent,false)

        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        Log.d("adapterlist","list of images $videoList")
        holder.view.gallaryVideo.loadImage(videoList[position],
            getProgressDrawable(holder.view.context)
        )
        //convert arraylist ot array
        val videolistarray: Array<String> = videoList.toTypedArray()

        //convert array to arrlist
//        val video_list: List<String> = vowels_array.toList()

        holder.view.videoLayout.setOnClickListener {
            val action = VideoFragmentDirections.actionvideoFragmenttoPlayvideoFragment(videolistarray,position)
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
    fun updateVideoList(newVideoList:List<String>){
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()

    }

}//end of VideoListAdapter