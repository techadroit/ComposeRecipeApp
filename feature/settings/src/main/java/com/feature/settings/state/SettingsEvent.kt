package com.feature.settings.state

import com.archerviewmodel.events.ArcherEvent
import com.domain.common.pojo.Cuisine

sealed class SettingsEvent : ArcherEvent

object InitializeSettings : SettingsEvent()

data class ChangeDarkModeSettings(val isDarkModeOn: Boolean) : SettingsEvent()

data class CuisineSelected(val cuisine: Cuisine) : SettingsEvent()
data class CuisineDeSelected(val cuisine: Cuisine) : SettingsEvent()
object SaveCuisine : SettingsEvent()
