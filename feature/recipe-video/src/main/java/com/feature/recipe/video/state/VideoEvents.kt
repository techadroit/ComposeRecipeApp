package com.feature.recipe.video.state

import com.state_manager.events.AppEvent

sealed class VideoEvents : AppEvent

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents()

object RefreshVideoScreen : VideoEvents()
