package io.jmdg.xtrengthapp

import android.app.Application
import io.jmdg.xtrength.BuildConfig
import timber.log.Timber

/**
 * Created by Joshua de Guzman on 05/08/2018.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}