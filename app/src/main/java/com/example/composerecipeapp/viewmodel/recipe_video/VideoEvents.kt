package com.example.composerecipeapp.viewmodel.recipe_video

import com.archerviewmodel.events.ArcherEvent

sealed class VideoEvents : ArcherEvent

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents()
