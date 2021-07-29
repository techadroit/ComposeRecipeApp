package com.example.composerecipeapp.viewmodel.user_interest

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.core.functional.Consumable

data class UserInterestState(
    val cuisines: List<Cuisine> = emptyList(),
    val enableNextOptions: Boolean = false,
    val sideEffect: Consumable<SideEffect> ?= null
) : ArcherState

data class Cuisine(val name: String, val isSelected: Boolean = false)

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
