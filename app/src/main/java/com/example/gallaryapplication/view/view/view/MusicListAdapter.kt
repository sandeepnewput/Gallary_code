package com.example.gallaryapplication.view.view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.ImageItemBinding
import com.example.gallaryapplication.databinding.MusicItemsBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class MusicViewHolder(
    private val binding: MusicItemsBinding,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {
        binding.galleryMusic.loadImage(
            uri,
            getProgressDrawable(binding.galleryMusic.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind method

}//end of ImageViewHolder

class MusicListAdapter(
    private var musicList: List<String>,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.Adapter<MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemBinding =
            MusicItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(musicList[position])
    }//end of onBindViewHolder

    override fun getItemCount(): Int {
        return musicList.count()
    }

    fun updateMusicList(newMusicList: List<String>) {
        musicList = newMusicList
        notifyDataSetChanged()
    }

}//end of imagelistadapter