package com.example.composerecipeapp.ui

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.composerecipeapp.core.network.NetworkHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var networkHandler: NetworkHandler

    override fun onCreate() {
        super.onCreate()
        networkHandler.init(null)
    }
}
