package com.filemanager.explorer.di

import android.os.Environment
import androidx.annotation.VisibleForTesting
import com.filemanager.common.ui.extensions.viewModelOf
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.core.ultils.FileUtils
import com.filemanager.explorer.view.FileManagerFragment
import com.filemanager.explorer.view.ListFileFragment
import com.filemanager.explorer.view.ListFileFragment.Companion.ARG_PATH
import com.filemanager.explorer.viewmodel.FileManagerFragmentViewModel
import com.filemanager.explorer.viewmodel.ListFileFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class FileManagerFragmentModule(
    @get:VisibleForTesting
    val fragment: FileManagerFragment
) {

    @Provides
    @FeatureScope
    fun provideFragmentViewModel(): FileManagerFragmentViewModel =
        fragment.viewModelOf {
            FileManagerFragmentViewModel()
        }
}