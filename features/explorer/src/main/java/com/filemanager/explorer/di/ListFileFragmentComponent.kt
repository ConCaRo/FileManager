package com.filemanager.explorer.di

import com.filemanager.core.di.CoreComponent
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.explorer.view.ListFileFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [ListFileFragmentModule::class],
    dependencies = [CoreComponent::class]
)
interface ListFileFragmentComponent {

    fun inject(fragment: ListFileFragment)
}