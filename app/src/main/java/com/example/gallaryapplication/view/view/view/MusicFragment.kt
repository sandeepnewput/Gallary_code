package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentMusicBinding
import com.example.gallaryapplication.view.view.model.AudioModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MusicFragment : BaseFragment<FragmentMusicBinding>() {

    private val listAdapter = MusicListAdapter(this::onClickMedia)

    private val viewModel: SharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMusicBinding? {
        return FragmentMusicBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        val item1 = AudioModel(1, "J", "Java")
        val item2 = AudioModel(2, "K", "Kotlin")
        val item3 = AudioModel(3, "A", "Android")
        val item4 = AudioModel(4, "P", "Python")
        val item5 = AudioModel(5, "L", "Laravel")
        val item6 = AudioModel(6, "RN", "React Native")

        listAdapter.submitList(listOf(item1, item2, item3, item4, item5, item6))

        Handler(Looper.getMainLooper()).postDelayed({
            val item1 = AudioModel(1, "J", "Ram")
            val item2 = AudioModel(2, "K", "Shyam")
            val item3 = AudioModel(3, "A", "Android")
            val item4 = AudioModel(4, "P", "Python")
            val item5 = AudioModel(5, "L", "Laravel")
            val item6 = AudioModel(6, "RN", "React Native")
            val item7 = AudioModel(7, "B", "Bahubali")


            listAdapter.submitList(listOf(item1, item2, item3, item4, item5, item6, item7))

        }, 4000)

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }//end of onViewCreatedView Method


    private fun onClickMedia(uri: String) {
//        viewModel.setCurrentMusicUri(uri)
        findNavController().navigate(R.id.action_global_PlayMusicFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }

}//end of PhotoFragment