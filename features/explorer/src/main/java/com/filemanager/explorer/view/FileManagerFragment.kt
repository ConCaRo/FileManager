package com.filemanager.explorer.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.filemanager.FileManagerApplication.Companion.coreComponent
import com.filemanager.common.ui.base.BaseFragment
import com.filemanager.common.ui.extensions.*
import com.filemanager.core.entity.FileModel
import com.filemanager.explorer.adapter.ListFileAdapter
import com.filemanager.explorer.di.DaggerFileManagerFragmentComponent
import com.filemanager.explorer.di.FileManagerFragmentModule
import com.filemanager.explorer.viewmodel.FileManagerFragmentViewModel
import com.filemanager.features.explorer.R
import com.filemanager.features.explorer.databinding.FragmentManagerBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

/**
 * This is container for 2 child fragments [BreadcrumbFragment] and [ListFileFragment]
 * The container shares [FileManagerFragmentViewModel] for child fragments
 */
class FileManagerFragment :
    BaseFragment<FragmentManagerBinding, FileManagerFragmentViewModel>(R.layout.fragment_manager)  {

    override fun onInitDependencyInjection() {
        DaggerFileManagerFragmentComponent.builder()
            .coreComponent(coreComponent(requireContext()))
            .fileManagerFragmentModule(FileManagerFragmentModule(this))
            .build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding?.lifecycleOwner = viewLifecycleOwner
        viewBinding?.vm = viewModel
        observeChanges()
    }

    override fun setView() {
        requireActivity().onBackPressedDispatcher.addCallback(owner = this) {
            if (getStackCountListFileFragment() == 1) {
                requireActivity().finish()
            } else {
                viewModel.popFromStack()
            }
        }
        showFileManager()
    }

    private fun observeChanges() {
        viewModel.stackFiles.observe(viewLifecycleOwner) {
            val backStackEntryCount = getStackCountListFileFragment()
            if (it.size < backStackEntryCount ) {   // click on breadcrumb
                childFragmentManager.popBackStack(
                    it[it.size - 1].path,
                    2
                )
            } else if (it.size > backStackEntryCount) { // add 1 item to breadcrumb
                addFileManagerFragment(it[it.size - 1].path)
            }
        }
    }

    private fun addBreadcrumbFragment() {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentBreadcrumb, BreadcrumbFragment())
        fragmentTransaction.commit()
    }

    /**
     * Responsible for adding new list file when user click to a folder
     * Make a transaction and add the current fragment list file to the stack
     */
    private fun addFileManagerFragment(path: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.fragmentListFile, ListFileFragment.newInstance(path))
        fragmentTransaction.addToBackStack(path)
        fragmentTransaction.commit()
    }

    private fun getStackCountListFileFragment() :Int {
        // Default we have 2 fragment in stack: 1 - BreadcrumbFragment, 2 ListFileFragment
        // we should minus 1 for BreadcrumbFragment
        // TODO: should fixing working around here
        return childFragmentManager?.backStackEntryCount - 1
    }

    private fun showFileManager() {
        if (checkSelfPermissionCompat(Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            addBreadcrumbFragment()
            addFileManagerFragment(Environment.getExternalStorageDirectory().absolutePath)
        } else {
            requestStoragePermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewBinding.root.showSnackbar("Please grant READ your storage", Snackbar.LENGTH_SHORT)
                showFileManager()
            } else {
                viewBinding.root.showSnackbar("Please grant READ your storage", Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun requestStoragePermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            viewBinding.root.showSnackbar("Please grant READ your storage",
                Snackbar.LENGTH_INDEFINITE, "ok") {
                requestPermissionsCompat(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            viewBinding.root.showSnackbar("Please grant READ your storage", Snackbar.LENGTH_SHORT)
            requestPermissionsCompat(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
        }
    }
}