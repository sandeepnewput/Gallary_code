package com.example.gallaryapplication.view.view.view


import android.os.Bundle
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
import com.example.gallaryapplication.view.view.model.MusicModel
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

    private fun onClickMedia(musicModel: MusicModel) {
        viewModel.setCurrentMusicUri(musicModel)
        findNavController().navigate(R.id.action_global_PlayMusicFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }

}//end of PhotoFragment