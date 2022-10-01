package com.filemanager.explorer.di

import android.os.Environment
import androidx.annotation.VisibleForTesting
import com.filemanager.common.ui.extensions.viewModelOf
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.core.ultils.FileUtils
import com.filemanager.explorer.view.ListFileFragment
import com.filemanager.explorer.viewmodel.FileManagerFragmentViewModel
import com.filemanager.explorer.viewmodel.ListFileFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class ListFileFragmentModule(
    @get:VisibleForTesting
    val fragment: ListFileFragment
) {

    @Provides
    @FeatureScope
    fun provideFragmentViewModel(fileUtils: FileUtils): ListFileFragmentViewModel =
        fragment.viewModelOf {
            val path = fragment.arguments?.getString(ListFileFragment.ARG_PATH)
                ?: Environment.getExternalStorageDirectory().absolutePath
            ListFileFragmentViewModel(fileUtils, path)
        }

}