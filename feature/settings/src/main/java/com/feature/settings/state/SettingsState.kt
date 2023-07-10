package com.feature.settings.state

import com.core.platform.functional.ViewEffect
import com.domain.common.pojo.Cuisine
import com.state_manager.extensions.Consumable
import com.state_manager.extensions.asConsumable
import com.state_manager.state.AppState

data class SettingsState(
    val isDarkModeOn: Boolean = false,
    val list: List<Cuisine> = emptyList(),
    val enableSaveOptions: Boolean = false,
    val viewEffect: Consumable<ViewEffect>? = null
) : AppState

fun SettingsState.deselectCuisine(cuisine: Cuisine): SettingsState = run {
    val newList = list.map {
        if (it == cuisine) {
            it.copy(isSelected = false)
        } else {
            it
        }
    }
    return copy(list = newList, enableSaveOptions = enableSaveOptions(newList))
}

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

fun SettingsState.getSelectedCuisine() = list.filter { it.isSelected }

object CuisinePreferencesSaved : ViewEffect()
