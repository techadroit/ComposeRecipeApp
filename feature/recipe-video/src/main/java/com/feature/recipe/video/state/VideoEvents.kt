package com.feature.recipe.video.state

import com.archerviewmodel.events.ArcherEvent

sealed class VideoEvents : ArcherEvent

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents()

object RefreshVideoScreen : VideoEvents()