package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallaryapplication.databinding.VideoItemBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

private const val ARG_DONE = "uri"

class VideoViewHolder(
    private val binding: VideoItemBinding,
    private val onClickMedia: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: String) {
        binding.gallaryVideo.loadImage(
            uri,
            getProgressDrawable(binding.gallaryVideo.context)
        )
        binding.root.setOnClickListener { onClickMedia(uri) }
    }//end of bind function

    fun update(bundle: Bundle) {
        if (bundle.containsKey(ARG_DONE)) {
            val uri = bundle.getString(ARG_DONE)
            binding.gallaryVideo.loadImage(
                uri,
                getProgressDrawable(binding.gallaryVideo.context)
            )
        }
    }
}// end of VideoViewHolder

class VideoDiffCallback :
    DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) =
        oldItem.toUri() === newItem.toUri()

    override fun areContentsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    override fun getChangePayload(oldItem: String, newItem: String): Any? {

        if (oldItem == newItem) {
            return if (oldItem == newItem) {
                super.getChangePayload(oldItem, newItem)
            } else {

                val diff = Bundle()
                diff.putString(ARG_DONE, newItem.toUri().toString())
                return diff
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }

}//end of ImageDiffCallback


class VideoListAdapter(
    private val onClickMedia: (String) -> Unit
) : ListAdapter<String, VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemBinding =
            VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding, onClickMedia)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }//end of onBindViewHolder mehtod

    override fun onBindViewHolder(holder: VideoViewHolder, pos: Int, payload: List<Any>) {

        if (payload.isEmpty() || payload[0] !is Bundle) {
            holder.bind(getItem(pos))
        } else {
            val bundle = payload[0] as Bundle
            holder.update(bundle)
        }
    }

}//end of VideoListAdapter


