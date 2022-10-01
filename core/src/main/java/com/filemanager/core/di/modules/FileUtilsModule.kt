package com.filemanager.core.di.modules

import android.app.Application
import android.content.Context
import com.filemanager.core.ultils.FileUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
class FileUtilsModule {

    /**
     * Create a provider method binding for [FileUtils].
     *
     * @return Instance of FileUtils.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideFileUtils(): FileUtils = FileUtils()
}
