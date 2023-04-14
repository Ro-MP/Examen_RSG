package com.picazodev.electroniclogistica.di

import android.app.Application
import com.picazodev.electroniclogistica.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class ElectronicApplication: Application(){


    override fun onCreate() {
        super.onCreate()


        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }


}