package com.data.repository.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(val dataStore: DataStore<Preferences>) {

    private val darkModeOn = booleanPreferencesKey("dark_mode")
    private val cuisines = stringPreferencesKey("cuisines")

    suspend fun addDarkModeOn(isSelected: Boolean) {
        try {
            dataStore.edit {
                it[darkModeOn] = isSelected
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isDarkModeOn(): Flow<Boolean?> =
        dataStore.data.map {
            it[darkModeOn]
        }

    suspend fun storeCuisine(list: List<String>) {
        dataStore.edit {
            it[cuisines] = list.joinToString(separator = ",")
        }
    }

    fun getCuisines(): Flow<List<String>> = dataStore.data.map {
        it[cuisines]?.split(",") ?: emptyList()
    }
}
