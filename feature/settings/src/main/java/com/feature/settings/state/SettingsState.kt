package com.feature.settings.state

import com.state_manager.state.AppState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.core.platform.functional.asConsumable
import com.domain.common.pojo.Cuisine

data class SettingsState(
    val isDarkModeOn: Boolean = false,
    val list: List<Cuisine> = emptyList(),
    val enableSaveOptions: Boolean = false,
    val viewEffect: Consumable<ViewEffect>? = null
) : AppState

fun SettingsState.onCuisineSelected(list: List<Cuisine>) =
    copy(list = list, enableSaveOptions = enableSaveOptions(list))

fun SettingsState.enableSaveOptions(list: List<Cuisine>) =
    list.count { it.isSelected } == 5

fun SettingsState.onCuisineSaved() =
    copy(viewEffect = CuisinePreferencesSaved.asConsumable())

fun SettingsState.initialize(isDarkModeOn: Boolean, list: List<Cuisine>) = copy(
    isDarkModeOn = isDarkModeOn,
    list = list,
    enableSaveOptions = enableSaveOptions(list)
)

object CuisinePreferencesSaved : ViewEffect()
