package com.example.composerecipeapp.ui

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
