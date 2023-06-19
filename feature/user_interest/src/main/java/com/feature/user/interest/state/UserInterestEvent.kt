package com.feature.user.interest.state

import com.state_manager.events.AppEvent
import com.domain.common.pojo.Cuisine

sealed class UserInterestEvent : AppEvent

object LoadSupportedCuisine : UserInterestEvent()

data class SelectedCuisine(val cuisine: Cuisine) : UserInterestEvent()

data class RemoveCuisine(val cuisine: Cuisine) : UserInterestEvent()

object UserInterestSelected : UserInterestEvent()
