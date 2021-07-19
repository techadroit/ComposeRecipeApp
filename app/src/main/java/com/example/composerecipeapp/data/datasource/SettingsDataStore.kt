package com.example.composerecipeapp.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.composerecipeapp.ui.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsDataStore(val context: Context) {

    private val darkModeOn = booleanPreferencesKey("dark_mode")

    suspend fun addDarkModeOn(isSelected: Boolean){
        try {
            context.dataStore.edit {
                it[darkModeOn] = isSelected
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isDarkModeOn() : Flow<Boolean> =
        context.dataStore.data.map {
            it[darkModeOn] ?: false
        }
}
