package com.strikalov.exchangeratesofbanks

import android.app.Application
import com.strikalov.exchangeratesofbanks.di.AppModule
import com.strikalov.exchangeratesofbanks.di.DI
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initLogger()
        initAppScope()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppScope() {
        Toothpick.openScope(DI.APP_SCOPE).installModules(AppModule(this))
    }
}