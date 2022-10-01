package com.filemanager.explorer.view

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.filemanager.FileManagerApplication.Companion.coreComponent
import com.filemanager.common.ui.base.BaseFragment
import com.filemanager.explorer.adapter.ListFileAdapter
import com.filemanager.explorer.di.DaggerListFileFragmentComponent
import com.filemanager.explorer.di.ListFileFragmentModule
import com.filemanager.explorer.viewmodel.FileManagerFragmentViewModel
import com.filemanager.explorer.viewmodel.ListFileFragmentViewModel
import com.filemanager.features.explorer.R
import com.filemanager.features.explorer.databinding.FragmentListFileBinding

/**
 * Responsible for render list file
 */
class ListFileFragment :
    BaseFragment<FragmentListFileBinding, ListFileFragmentViewModel>(R.layout.fragment_list_file) {

    companion object {
        const val ARG_PATH: String = "path"

        fun newInstance(path: String): ListFileFragment {
            val fragment = ListFileFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: ListFileAdapter
    private val sharedViewModel: FileManagerFragmentViewModel by viewModels({ requireParentFragment() })

    override fun onInitDependencyInjection() {
        DaggerListFileFragmentComponent.builder()
            .coreComponent(coreComponent(requireContext()))
            .listFileFragmentModule(ListFileFragmentModule(this))
            .build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.vm = viewModel
        observeChanges()
    }

    override fun setView() {
        adapter = ListFileAdapter {
            if (it.isFolder) {
                sharedViewModel.addToStackFiles(fileModel = it)
            } else {
                viewModel.fileUtils.launchFileIntent(requireContext(), it, "Choose Application")
            }
        }
        viewBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@ListFileFragment.adapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeChanges() {
        viewModel.files.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}