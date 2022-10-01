package com.filemanager

import android.app.Application
import android.content.Context
import com.filemanager.core.di.CoreComponent
import com.filemanager.core.di.DaggerCoreComponent
import com.filemanager.core.di.modules.ContextModule
import com.filemanager.di.DaggerAppComponent

class FileManagerApplication : Application() {

    lateinit var coreComponent: CoreComponent

    companion object {

        /**
         * Provide the core component for app component
         *
         * @return [CoreComponent]
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? FileManagerApplication)?.coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initCoreDependencyInjection()
        initAppDependencyInjection()
    }

    /**
     * Initialize application dependency injection component.
     */
    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }
}