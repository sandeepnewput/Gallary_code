package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.MusicItemsBinding
import com.example.gallaryapplication.view.view.model.AudioModel

private const val ARG_UPDATE_INITIALS = "name"
private const val ARG_UPDATE_LANGUAGE = "language"

class MusicViewHolder(
    private val binding: MusicItemsBinding,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(audioModel: AudioModel) {
        binding.initials.text = audioModel.initials
        binding.languageName.text = audioModel.languageName
    }//end of bind method

    fun update(bundle: Bundle) {
        if (bundle.containsKey(ARG_UPDATE_INITIALS) || bundle.containsKey(ARG_UPDATE_LANGUAGE)) {
            val initials = bundle.getString(ARG_UPDATE_INITIALS)
            val languageName = bundle.getString(ARG_UPDATE_LANGUAGE)
            binding.initials.text = initials
            binding.languageName.text = languageName
        }
    }

}//end of ImageViewHolder

class MusicDiffCallback : DiffUtil.ItemCallback<AudioModel>() {

    override fun areItemsTheSame(oldItem: AudioModel, newItem: AudioModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AudioModel, newItem: AudioModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: AudioModel, newItem: AudioModel): Any? {

       bundleOf().apply {
           putString(ARG_UPDATE_INITIALS, newItem.initials)
           putString(ARG_UPDATE_LANGUAGE, newItem.languageName)
           return@apply
       }
        return super.getChangePayload(oldItem, newItem)
    }

}//end of ImageDiffCallback


class MusicListAdapter(
    private val onClickMedia: (String) -> Unit
) : ListAdapter<AudioModel, MusicViewHolder>(MusicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemBinding =
            MusicItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }//end of onBindViewHolder

    override fun onBindViewHolder(holder: MusicViewHolder, pos: Int, payload: List<Any>) {

        if (payload.isEmpty() || payload[0] !is Bundle) {
            holder.bind(getItem(pos))
        } else {
            val bundle = payload[0] as Bundle
            holder.update(bundle)
        }
    }

}//end of imagelistadapter