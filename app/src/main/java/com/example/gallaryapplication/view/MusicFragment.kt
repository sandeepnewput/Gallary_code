package com.example.gallaryapplication.view



import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    private val sharedViewModel: MusicSharedViewModel by activityViewModels()

    private val viewModel: MusicViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMusicBinding? {
        return FragmentMusicBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {it->
            PermissionLauncher.requestPermission(
                this,
                it,
                listener
            ).launch(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        viewModel.getAllUserMusic()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            scrollToPosition(sharedViewModel.currentMusicIndexPosition)
        }
        viewModel.userMusic.observe(viewLifecycleOwner){
            sharedViewModel.updateUserMusicList(it)
        }

        sharedViewModel.userMusic.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }//end of onViewCreatedView Method

    private val listener = object : Listener {
        override fun permissionAllowed() {
             viewModel.getAllUserMusic()
        }

        override fun permissionDenied() {
            Log.d("permission","permission is denied")
        }

        override fun showRationalForPermission() {

            activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle(getString(R.string.permission))
                    setMessage(
                        getString(R.string.permission_required)
                    )
                    setPositiveButton(R.string.setting,
                        DialogInterface.OnClickListener { dialog, id ->
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                data = Uri.fromParts(
                                    "package",
                                    "com.example.gallaryapplication",
                                    "MediaFragment"
                                )
                                ContextCompat.startActivity(context, this, null)
                            }
                        })
                    setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
                    show()
                }
                builder.create()
            }//end of dialog box
        }

    }




    private fun onClickMedia(mediaModel: MediaModel) {
        sharedViewModel.setCurrentMusicUri(mediaModel)
        findNavController().navigate(R.id.action_global_PlayMusicFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }



}//end of PhotoFragment