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

                else -> false
            }
        }

    }


    private fun setFragment(fragmentId: Int?) {
        activity?.supportFragmentManager?.beginTransaction()?.run {
            when (fragmentId) {
                1 -> {
                    replace(R.id.frameLayout, PhotoFragment())
                    commit()
                }
                else -> {
                    replace(R.id.frameLayout, VideoFragment())
                    commit()
                }
            }

        }
    }


}//end of bottomnavigation fragment