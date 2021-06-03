package com.example.composerecipeapp.ui

import android.app.Application
import com.example.composerecipeapp.core.network.NetworkHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(){

    @Inject lateinit var networkHandler : NetworkHandler

    override fun onCreate() {
        super.onCreate()
        networkHandler.init(null)
    }
}
