package com.example.gallaryapplication.view.view.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.MusicItemsBinding
import com.example.gallaryapplication.view.view.model.MediaModel


class MusicViewHolder(
    private val binding: MusicItemsBinding,
    private val onClickMedia: (MediaModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(musicModel: MediaModel) {
       binding.musicName.text = musicModel.name
        binding.root.setOnClickListener { onClickMedia(musicModel) }
    }//end of bind method

}//end of ImageViewHolder

class MusicDiffCallback : DiffUtil.ItemCallback<MediaModel>() {

    override fun areItemsTheSame(oldItem: MediaModel, newItem: MediaModel): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: MediaModel, newItem: MediaModel): Boolean {
        return oldItem == newItem
    }
}//end of ImageDiffCallback


class MusicListAdapter(
    private val onClickMedia: (MediaModel) -> Unit
) : ListAdapter<MediaModel, MusicViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemBinding =
            MusicItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
       holder.bind(getItem(position))
    }//end of onBindViewHolder



}//end of imagelistadapter