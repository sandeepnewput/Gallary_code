package com.example.gallaryapplication.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentMusicBinding
import com.example.gallaryapplication.model.MediaModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MusicFragment : BaseFragment<FragmentMusicBinding>() {

    private val listAdapter = MusicListAdapter(this::onClickMedia)

    private val viewModel: MusicSharedViewModel by activityViewModels()

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
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            scrollToPosition(viewModel.currentMusicIndexPosition)
        }
        viewModel.userMusic.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }//end of onViewCreatedView Method


//    override fun getMediaFiles() = localviewmodel.getAllUserMusic()

    private fun onClickMedia(mediaModel: MediaModel) {
        viewModel.setCurrentMusicUri(mediaModel)
        findNavController().navigate(R.id.action_global_PlayMusicFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }

}//end of PhotoFragment