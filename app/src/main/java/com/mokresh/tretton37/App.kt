package com.mokresh.tretton37

import android.app.Application
import android.content.Context
import com.mokresh.tretton37.di.AppKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeInjector()

        instance = this


    }

    private fun initializeInjector() {
        startKoin {
            // declare used Android context
            //            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(AppKoinModules.getModules())
        }
    }

    companion object {

        /**
         * Gets an instance of the main application.
         */
        var instance: App? = null
            private set

        /**
         * Gets a context of the main application.
         */
        val context: Context?
            get() = instance?.applicationContext
    }


}