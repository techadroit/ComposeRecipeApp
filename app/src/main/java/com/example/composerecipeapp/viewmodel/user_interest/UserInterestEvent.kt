package com.example.composerecipeapp.viewmodel.user_interest

import com.archerviewmodel.events.ArcherEvent

sealed class UserInterestEvent : ArcherEvent

object LoadSupportedCuisine : UserInterestEvent()

data class SelectedCuisine(val cuisine: Cuisine) : UserInterestEvent()

data class RemoveCuisine(val cuisine: Cuisine) : UserInterestEvent()

object UserInterestSelected : UserInterestEvent()


