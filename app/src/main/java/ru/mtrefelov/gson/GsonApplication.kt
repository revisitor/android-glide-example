package ru.mtrefelov.gson

import android.app.Application
import timber.log.Timber

class GsonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}