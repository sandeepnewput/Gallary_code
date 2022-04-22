package com.example.gallaryapplication.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentBottomNavigationViewBinding
import com.example.gallaryapplication.model.MediaType


const val MEDIA_TYPE = "Fragment Type"

class BottomNavigationViewFragment : BaseFragment<FragmentBottomNavigationViewBinding>() {

    private val viewModel: BottomNavigationViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBottomNavigationViewBinding? {
        return FragmentBottomNavigationViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mediaType.observe(viewLifecycleOwner) {
            setFragment(it)
        }
        binding.bottomNavigationView.setOnItemSelectedListener {

            if (binding.bottomNavigationView.selectedItemId != it.itemId) {
                when (it.itemId) {
                    R.id.Photos -> {
                        viewModel.updateFragment(MediaType.IMAGE)
                    }
                    R.id.Videos -> {
                        viewModel.updateFragment(MediaType.VIDEO)
                    }
                    R.id.Music -> {
                        viewModel.updateFragment(MediaType.MUSIC)
                    }
                    else -> {
                        viewModel.updateFragment(MediaType.FLICKR)
                    }
                }
            }
            true

        }
    }

    private fun setFragment(mediaType: MediaType) {
        activity?.supportFragmentManager?.beginTransaction()?.run {

            val fragment = when (mediaType) {
                MediaType.IMAGE, MediaType.VIDEO -> {
                    MediaFragment().apply {
                        arguments = bundleOf().apply {
                            putString(MEDIA_TYPE, mediaType.name)
                        }
                    }
                }
                MediaType.MUSIC -> MusicFragment()
                else -> FlickrImagesFragment()
            }
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}//end of bottomnavigation fragment