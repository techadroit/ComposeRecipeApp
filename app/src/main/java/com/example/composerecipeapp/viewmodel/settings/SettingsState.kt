package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.core.functional.Consumable
import com.example.composerecipeapp.core.functional.ViewEffect
import com.example.composerecipeapp.viewmodel.user_interest.Cuisine

data class SettingsState(
    val isDarkModeOn: Boolean = false,
    val list: List<Cuisine> = emptyList(),
    val enableSaveOptions: Boolean = false,
    val viewEffect: Consumable<ViewEffect> ? = null
) :
    ArcherState

object CuisinePreferencesSaved : ViewEffect()
