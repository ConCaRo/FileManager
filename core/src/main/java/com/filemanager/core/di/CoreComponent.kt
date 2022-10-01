package com.filemanager.core.di

import android.content.Context
import com.filemanager.core.di.modules.ContextModule
import com.filemanager.core.di.modules.FileUtilsModule
import com.filemanager.core.ultils.FileUtils
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        FileUtilsModule::class
    ]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph [FileUtils]
     *
     * @return FileUtils
     */
    fun fileUtils(): FileUtils
}