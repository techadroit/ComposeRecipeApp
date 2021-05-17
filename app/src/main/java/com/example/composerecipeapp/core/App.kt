package com.example.composerecipeapp.core

import android.app.Application
import com.example.composerecipeapp.core.network.NetworkHandler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkHandler.init(null)
    }
}