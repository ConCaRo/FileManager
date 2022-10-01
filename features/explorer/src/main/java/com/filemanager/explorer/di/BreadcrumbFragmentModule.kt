package com.filemanager.explorer.di

import androidx.annotation.VisibleForTesting
import com.filemanager.common.ui.extensions.viewModelOf
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.explorer.view.BreadcrumbFragment
import com.filemanager.explorer.viewmodel.BreadcrumbFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class BreadcrumbFragmentModule(
    @get:VisibleForTesting
    val fragment: BreadcrumbFragment
) {

    @Provides
    @FeatureScope
    fun provideFragmentViewModel(): BreadcrumbFragmentViewModel =
        fragment.viewModelOf {
            BreadcrumbFragmentViewModel()
        }
}