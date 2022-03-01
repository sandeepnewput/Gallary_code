package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentBottomNavigationViewBinding

class BottomNavigationViewFragment : BaseFragment<FragmentBottomNavigationViewBinding>() {

//    var navController: NavController? =
//        activity?.
//        supportFragmentManager?.findFragmentById(R.id.bottomNavigationViewFragment)?.findNavController()
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBottomNavigationViewBinding? {
        return FragmentBottomNavigationViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding.bottomNavigationView.menu.getItem(0).isCheckable = true
        setFragment(PhotoFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.Photos -> {

                    setFragment(PhotoFragment())

                    true
                }

                R.id.Videos -> {

                    setFragment(VideoFragment())
                    true
                }

                else -> false
            }
        }

    }


    private fun setFragment(fr : Fragment){
        val frag = activity?.supportFragmentManager?.beginTransaction()
        frag?.replace(R.id.frameLayout,fr)
        frag?.commit()
    }


    override fun backPressed() {
//        findNavController().navigate(R.id.action_global_bottomNavigationView)

    }


}//end of bottomnavigation fragment