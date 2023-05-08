package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.state.ArcherState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.domain.common.pojo.Cuisine

data class SettingsState(
    val isDarkModeOn: Boolean = false,
    val list: List<Cuisine> = emptyList(),
    val enableSaveOptions: Boolean = false,
    val viewEffect: Consumable<ViewEffect> ? = null
) :
    ArcherState

object CuisinePreferencesSaved : ViewEffect()