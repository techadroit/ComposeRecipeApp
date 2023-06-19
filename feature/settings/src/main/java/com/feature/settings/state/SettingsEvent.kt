package com.feature.settings.state

import com.state_manager.events.AppEvent
import com.domain.common.pojo.Cuisine

sealed class SettingsEvent : AppEvent

object InitializeSettings : SettingsEvent()

data class ChangeDarkModeSettings(val isDarkModeOn: Boolean) : SettingsEvent()

data class CuisineSelected(val cuisine: Cuisine) : SettingsEvent()
data class CuisineDeSelected(val cuisine: Cuisine) : SettingsEvent()
object SaveCuisine : SettingsEvent()
