package com.feature.user.interest.state

import com.archerviewmodel.events.ArcherEvent
import com.domain.common.pojo.Cuisine

sealed class UserInterestEvent : ArcherEvent

object LoadSupportedCuisine : UserInterestEvent()

data class SelectedCuisine(val cuisine: Cuisine) : UserInterestEvent()

data class RemoveCuisine(val cuisine: Cuisine) : UserInterestEvent()

object UserInterestSelected : UserInterestEvent()
