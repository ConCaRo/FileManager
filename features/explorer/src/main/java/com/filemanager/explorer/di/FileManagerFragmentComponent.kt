package com.filemanager.explorer.di

import com.filemanager.core.di.CoreComponent
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.explorer.view.FileManagerFragment
import com.filemanager.explorer.view.ListFileFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [FileManagerFragmentModule::class],
    dependencies = [CoreComponent::class]
)
interface FileManagerFragmentComponent {

    fun inject(fragment: FileManagerFragment)
}