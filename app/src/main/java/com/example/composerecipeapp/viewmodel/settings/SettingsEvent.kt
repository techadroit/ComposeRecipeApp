package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.events.ArcherEvent
import com.example.composerecipeapp.viewmodel.user_interest.Cuisine

sealed class SettingsEvent : ArcherEvent

object InitializeSettings : SettingsEvent()

data class ChangeDarkModeSettings(val isDarkModeOn: Boolean) : SettingsEvent()

data class CuisineSelected(val cuisine: Cuisine) : SettingsEvent()
data class CuisineDeSelected(val cuisine: Cuisine) : SettingsEvent()
object SaveCuisine : SettingsEvent()
