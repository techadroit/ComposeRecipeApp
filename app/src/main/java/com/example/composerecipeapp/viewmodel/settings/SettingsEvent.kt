package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.events.ArcherEvent

sealed class SettingsEvent : ArcherEvent

object InitializeSettings : SettingsEvent()

data class ChangeDarkModeSettings(val isDarkModeOn: Boolean) : SettingsEvent()
