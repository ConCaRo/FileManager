package com.filemanager.explorer.view

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.filemanager.FileManagerApplication.Companion.coreComponent
import com.filemanager.common.ui.base.BaseFragment
import com.filemanager.explorer.adapter.BreadcrumbAdapter
import com.filemanager.explorer.di.BreadcrumbFragmentModule
import com.filemanager.explorer.di.DaggerBreadcrumbFragmentComponent
import com.filemanager.explorer.viewmodel.BreadcrumbFragmentViewModel
import com.filemanager.explorer.viewmodel.FileManagerFragmentViewModel
import com.filemanager.features.explorer.R
import com.filemanager.features.explorer.databinding.FragmentBreadcrumbBinding

/**
 * Responsible for render  breadcrumb on the top of list file
 */
class BreadcrumbFragment :
    BaseFragment<FragmentBreadcrumbBinding, BreadcrumbFragmentViewModel>(R.layout.fragment_breadcrumb) {

    private lateinit var adapter: BreadcrumbAdapter
    private val sharedViewModel: FileManagerFragmentViewModel by viewModels({ requireParentFragment() })

    override fun onInitDependencyInjection() {
        DaggerBreadcrumbFragmentComponent.builder()
            .coreComponent(coreComponent(requireContext()))
            .breadcrumbFragmentModule(BreadcrumbFragmentModule(this))
            .build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.vm = viewModel
        observeChanges()
    }

    override fun setView() {
        adapter = BreadcrumbAdapter {
            sharedViewModel.popFromStackTill(fileModel = it)
        }
        viewBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@BreadcrumbFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeChanges() {
        sharedViewModel.stackFiles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewBinding.recyclerview.smoothScrollToPosition(it.size - 1)
        }
    }
}