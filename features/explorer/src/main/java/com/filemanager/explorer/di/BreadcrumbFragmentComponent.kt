package com.filemanager.explorer.di

import com.filemanager.core.di.CoreComponent
import com.filemanager.core.di.scopes.FeatureScope
import com.filemanager.explorer.view.BreadcrumbFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [BreadcrumbFragmentModule::class],
    dependencies = [CoreComponent::class]
)
interface BreadcrumbFragmentComponent {

    fun inject(fragment: BreadcrumbFragment)
}