package com.feature.user.interest.state

import com.state_manager.state.AppState
import com.core.platform.functional.ViewEffect
import com.domain.common.pojo.Cuisine
import com.state_manager.extensions.Consumable
import com.state_manager.extensions.asConsumable
import com.state_manager.side_effects.SideEffect

object OnCuisineSelected : SideEffect

data class UserInterestState(
    val cuisines: List<Cuisine> = emptyList(),
    val enableNextOptions: Boolean = false,
) : AppState

fun UserInterestState.onCuisineSelected(cuisine: Cuisine): UserInterestState {
    val newList = cuisines.map { if (it == cuisine) it.copy(isSelected = true) else it }
    val enableNextOptions = newList.count { it.isSelected } >= 5
    return copy(cuisines = newList, enableNextOptions = enableNextOptions)
}

fun UserInterestState.onCuisineRemoved(cuisine: Cuisine): UserInterestState {
    val newList = cuisines.map { if (it == cuisine) it.copy(isSelected = false) else it }
    val enableNextOptions = newList.count { it.isSelected } >= 5
    return copy(cuisines = newList, enableNextOptions = enableNextOptions)
}
