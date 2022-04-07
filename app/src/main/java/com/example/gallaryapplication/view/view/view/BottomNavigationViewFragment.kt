package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentBottomNavigationViewBinding

class BottomNavigationViewFragment : BaseFragment<FragmentBottomNavigationViewBinding>() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBottomNavigationViewBinding? {
        return FragmentBottomNavigationViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fragmentId.observe(viewLifecycleOwner) {
            setFragment(it)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.Photos -> {
                    viewModel.updateFragment(1)
                    true
                }
                R.id.Videos -> {
                    viewModel.updateFragment(2)
                    true
                }
                R.id.Music -> {
                    viewModel.updateFragment(3)
                    true
                }
                R.id.Flickr ->{
                    viewModel.updateFragment(4)
                    true
                }

                else -> false
            }
        }
    }

    private fun setFragment(fragmentId: Int) {
        activity?.supportFragmentManager?.beginTransaction()?.run {

            val fragment = when (fragmentId) {
                1 -> PhotoFragment()
                3 -> MusicFragment()
                4 -> FlickrImagesFragment()
                else -> VideoFragment()
            }
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}//end of bottomnavigation fragment