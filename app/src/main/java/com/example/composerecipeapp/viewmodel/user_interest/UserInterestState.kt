package com.example.composerecipeapp.viewmodel.user_interest

import com.archerviewmodel.state.ArcherState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.domain.common.pojo.Cuisine

object OnCuisineSelected : ViewEffect()

data class UserInterestState(
    val cuisines: List<Cuisine> = emptyList(),
    val enableNextOptions: Boolean = false,
    val viewEffect: Consumable<ViewEffect> ? = null
) : ArcherState

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
